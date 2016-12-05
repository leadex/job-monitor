package ru.leadexsystems.startup.jobmonitor.common.util;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by ekabardinsky on 11/21/16.
 */
public class DbManager implements DateManager{

    private static final String NAME_REQUEST_COLLECTION = "searchRequests";
    private static final String SOURCE_FIELD = "source";
    private static final String QUERY_FIELD = "query";
    private static final String DATE_FIELD = "date";
    private static final String CLIENT_FIELD = "client";
    private static final String NAME_CLIENT_COLLECTION = "clients";
    private String database;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private ServerAddress serverAddress;
    private MongoCredential credential;
    private MongoClient client = null;
    final Morphia morphia = new Morphia();
    private Datastore datastore = null;


    public DbManager(String host, Integer port, String database, String login, String password) {
        this.database = database;
        serverAddress = new ServerAddress(host, port);
        credential = MongoCredential.createCredential(login, database, password.toCharArray());
    }


    @Override
    public Date getLastDate(String query, JobSource source) throws NotFoundParameterException {
        Document  document = getUniqSearchRequest(query, source);
        Date date = document.get(DATE_FIELD, Date.class);
        if (date == null) {
            date = new Date();
            setLastDate(date, query, source);
        }
        return date;
    }

    @Override
    public void setLastDate(Date date, String query, JobSource source) throws NotFoundParameterException {
        MongoCollection collection = getCollection(NAME_REQUEST_COLLECTION);
        collection.updateOne(getBsonByQuery(query, source),
                new Document("$set", new Document(DATE_FIELD, date)));
    }

    public List<Client> getClientsByQuery(String query, JobSource source) throws NotFoundParameterException {
        List<Client> res = new ArrayList<>();
        Document document = getUniqSearchRequest(query, source);
        List<ObjectId> listClientId = (List<ObjectId>) document.get(CLIENT_FIELD);

        for (ObjectId cur : listClientId){
            res.add(getClientById(cur));
        }
        return res;
    }

    public Client getClientById(ObjectId id) throws NotFoundParameterException {
        List<Document> documents = executeBsonQuery(NAME_CLIENT_COLLECTION, eq("_id", id));
        if (documents.size() == 0) {
            throw new IllegalStateException(String.format("Mongodb don't have client with id : %s", id));
        }
        if (documents.size() > 1) {
            throw new IllegalStateException(String.format("Mongodb has duplicate client with id : %s", id));
        }
        Document fisrtDocument = documents.get(0);
        Gson gson = new Gson();

        return gson.fromJson(fisrtDocument.toJson(), Client.class);
    }

    private List<Document> getSearchRequest(String query, JobSource source) throws NotFoundParameterException {
        return executeBsonQuery(NAME_REQUEST_COLLECTION, getBsonByQuery(query, source));
    }

    private Document getUniqSearchRequest(String query, JobSource source) throws NotFoundParameterException {
        List<Document> documents = getSearchRequest(query, source);
        if (documents.size() == 0) {
            throw new IllegalStateException("Mongodb don't have query");
        }
        if (documents.size() > 1) {
            throw new IllegalStateException("Mongodb has duplicate query to same application");
        }
        return documents.get(0);
    }


    private MongoCollection getCollection(String collection) throws NotFoundParameterException {
        if (!checkConnection()) {
            mongoClient = null;
        }
        if (mongoClient == null) {
            try {
                mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
                db = mongoClient.getDatabase(database);
            } catch (MongoException exception) {
                throw new NotFoundParameterException("mongodb is down");
            }
        }
        return db.getCollection(collection);
    }

    private List<Document> executeBsonQuery(String collection, Bson bson) throws NotFoundParameterException {
        MongoCollection mongoCollection = getCollection(collection);
        FindIterable findIterable = mongoCollection.find(bson);

        List<Document> documents = new ArrayList<>();
        findIterable.iterator().forEachRemaining((item) -> {
            documents.add((Document) item);
        });

        return documents;
    }


    private boolean checkConnection() {
        try {
            if (db != null) {
                db.getName();
            }
        } catch (MongoException exception) {
            return false;
        }
        return true;
    }

    private Bson getBsonByQuery(String query, JobSource source) {
        Document document = new Document();
        document.put(QUERY_FIELD, query);
        document.put(SOURCE_FIELD, source.toString());

        return document;
    }

    private MongoClient getClient() {
        if (client == null) {
            client = new MongoClient(serverAddress, Arrays.asList(credential));
        }
        return client;
    }
    public Datastore getDatastore() {
        if (datastore == null) {
            datastore = morphia.createDatastore(getClient(), database);
        }
        return datastore;
    }

}
