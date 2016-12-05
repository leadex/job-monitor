package jobmonitor.backend.api.util;

import com.mongodb.DBCursor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.common.model.SearchRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ekabardinsky on 11/24/16.
 */
public abstract class UserQueriesChangeHandler {
    public static void setAddedQueries(Datastore datastore, JobSource source, List<String> userQueries, ObjectId userId, List collection) {
        setQueries(datastore,
                source,
                userQueries,
                userId,
                collection,
                (condition, uQueries, uIds) -> {
                    Query<SearchRequest> query = condition
                            .field("query")
                            .hasAnyOf(userQueries);
                    if (uIds != null && uIds.size() > 0) {
                        query = query
                                .field("client")
                                .hasNoneOf(uIds);
                    }
                    return query;
                });
    }

    public static void setRemovedQueries(Datastore datastore, JobSource source, List<String> userQueries, ObjectId userId, List collection) {
        setQueries(datastore,
                source,
                userQueries,
                userId,
                collection,
                (condition, uQueries, uIds) -> {
                    Query<SearchRequest> query = condition
                            .field("client")
                            .hasAnyOf(uIds);

                    if (userQueries != null && userQueries.size() > 0) {
                        query = query
                                .field("query")
                                .hasNoneOf(userQueries);
                    }

                    return query;
                });
    }

    private static void setQueries(Datastore datastore,
                                   JobSource source,
                                   List<String> userQueries,
                                   ObjectId userId,
                                   List collection,
                                   ConditionApplier conditionApplier) {
        if (userQueries != null) {
            ArrayList<ObjectId> userIds = new ArrayList<>();
            if (userId != null) {
                userIds.add(userId);
            }

            //get all request where query was be added
            Query<SearchRequest> query = datastore.createQuery(SearchRequest.class)
                    .field("source")
                    .equal(source);

            Query<SearchRequest> applied = conditionApplier.apply(query, userQueries, userIds);
            collection.add(applied);
        }
    }

    public static List<Pair<JobSource, String>> checkMissingSearchRequest(Datastore datastore, List<Pair<JobSource, String>> queries) {

        List<JobSource> userSources = queries
                .stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
        List<String> userQueries = queries
                .stream()
                .map(Pair::getValue)
                .collect(Collectors.toList());

        Query<SearchRequest> query = datastore.createQuery(SearchRequest.class)
                .field("source")
                .hasAnyOf(userSources)
                .field("query")
                .hasAnyOf(userQueries);

        HashMap<JobSource, List<String>> exist = new HashMap<>();

        DBCursor dbObjects = datastore
                .getCollection(SearchRequest.class)
                .find(query.getQueryObject());

        dbObjects.forEachRemaining(x -> {
            Map map = x.toMap();
            JobSource existSource = JobSource.valueOf(map.get("source").toString());
            String existQuery = (String) map.get("query");
            if (exist.containsKey(existSource)) {
                exist.get(existSource).add(existQuery);
            } else {
                ArrayList<String> list = new ArrayList<String>();
                list.add(existQuery);
                exist.put(existSource, list);
            }
        });


        return queries
                .stream()
                .filter(x -> exist.containsKey(x.getKey()) ?
                        !exist.get(x.getKey()).contains(x.getValue()) : true)
                .collect(Collectors.toList());
    }

    public static void createMissingSearchRequests(Datastore datastore, List<Pair<JobSource, String>> missingRequests) {
        List<SearchRequest> requests = missingRequests
                .stream()
                .map(x -> {
                    SearchRequest newRequest = new SearchRequest();
                    newRequest.setSource(x.getKey());
                    newRequest.setQuery(x.getValue());
                    return newRequest;
                })
                .collect(Collectors.toList());
        datastore.save(requests);
    }

    public static void addClientToRequests(Datastore datastore, List<ObjectId> searchId, ObjectId clientId) {
        if (searchId != null && searchId.size() > 0) {
            Query<SearchRequest> query = datastore
                    .createQuery(SearchRequest.class)
                    .field("_id").hasAnyOf(searchId);

            UpdateOperations<SearchRequest> client = datastore
                    .createUpdateOperations(SearchRequest.class)
                    .add("client", clientId);

            datastore.update(query, client);
        }
    }

    public static void removeClientFromRequests(Datastore datastore, List<ObjectId> searchId, ObjectId clientId) {
        if (searchId != null && searchId.size() > 0) {
            Query<SearchRequest> query = datastore
                    .createQuery(SearchRequest.class)
                    .field("_id").hasAnyOf(searchId);

            UpdateOperations<SearchRequest> client = datastore
                    .createUpdateOperations(SearchRequest.class)
                    .removeAll("client", clientId);

            datastore.update(query, client);
        }
    }

    public static void handleAddedQueriesChange(Datastore datastore, Client client) {
        ArrayList<Query<SearchRequest>> addedQueries = new ArrayList<>();
        ru.leadexsystems.startup.jobmonitor.common.model.Query query = client.getQuery();
        List<Pair<JobSource, String>> userQueries = new ArrayList<>();

        if (query != null) {


            userQueries.addAll(query
                    .getFreelancer()
                    .stream()
                    .map(x -> new Pair<JobSource, String>(JobSource.freelancer, x))
                    .collect(Collectors.toList()));

            userQueries.addAll(query
                    .getUpwork()
                    .stream()
                    .map(x -> new Pair<>(JobSource.upwork, x))
                    .collect(Collectors.toList()));

            userQueries.addAll(query
                    .getGuru()
                    .stream()
                    .map(x -> new Pair<>(JobSource.guru, x))
                    .collect(Collectors.toList()));

            List<Pair<JobSource, String>> missingSearchRequest = checkMissingSearchRequest(datastore, userQueries);
            createMissingSearchRequests(datastore, missingSearchRequest);

            setAddedQueries(datastore, JobSource.upwork, query.getUpwork(), client.getId(), addedQueries);
            setAddedQueries(datastore, JobSource.freelancer, query.getFreelancer(), client.getId(), addedQueries);
            setAddedQueries(datastore, JobSource.guru, query.getGuru(), client.getId(), addedQueries);

            ArrayList<ObjectId> ids = new ArrayList<>();
            addedQueries.forEach((q) -> {
                DBCursor dbObjects = datastore.getCollection(SearchRequest.class).find(q.getQueryObject());

                dbObjects.forEachRemaining(x -> {
                    Map map = x.toMap();
                    ObjectId id = (ObjectId) map.get("_id");
                    ids.add(id);
                });
            });
            addClientToRequests(datastore, ids, client.getId());
        }
    }

    public static void handleRemovedQueriesChange(Datastore datastore, Client client) {
        ArrayList<Query<SearchRequest>> queries = new ArrayList<>();
        ru.leadexsystems.startup.jobmonitor.common.model.Query query = client.getQuery();

        if (query != null) {
            setRemovedQueries(datastore, JobSource.upwork, query.getUpwork(), client.getId(), queries);
            setRemovedQueries(datastore, JobSource.freelancer, query.getFreelancer(), client.getId(), queries);
            setRemovedQueries(datastore, JobSource.guru, query.getGuru(), client.getId(), queries);

            ArrayList<ObjectId> ids = new ArrayList<>();
            queries.forEach((q) -> {
                DBCursor dbObjects = datastore.getCollection(SearchRequest.class).find(q.getQueryObject());

                dbObjects.forEachRemaining(x -> {
                    Map map = x.toMap();
                    ObjectId id = (ObjectId) map.get("_id");
                    ids.add(id);
                });
            });
            removeClientFromRequests(datastore, ids, client.getId());

            Query<SearchRequest> removeQuery = datastore.createQuery(SearchRequest.class)
                    .field("client")
                    .sizeEq(0);
            datastore.delete(removeQuery);
        }
    }

    private interface ConditionApplier {
        Query<SearchRequest> apply(Query<SearchRequest> query, List<String> userQueries, ArrayList<ObjectId> userIds);
    }

    private static class Pair<K, V> implements Map.Entry<K, V>
    {
        private K key;
        private V value;

        public Pair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey()
        {
            return this.key;
        }

        public V getValue()
        {
            return this.value;
        }

        public K setKey(K key)
        {
            return this.key = key;
        }

        public V setValue(V value)
        {
            return this.value = value;
        }
    }
}
