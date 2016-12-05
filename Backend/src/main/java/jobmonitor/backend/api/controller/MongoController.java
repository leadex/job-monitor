package jobmonitor.backend.api.controller;

import com.mongodb.DBCursor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;
import ru.leadexsystems.startup.jobmonitor.common.util.DbManager;

import java.util.List;
import java.util.Map;

import static jobmonitor.backend.api.util.UserQueriesChangeHandler.handleAddedQueriesChange;
import static jobmonitor.backend.api.util.UserQueriesChangeHandler.handleRemovedQueriesChange;

/**
 * Created by ekabardinsky on 11/22/16.
 */
public class MongoController {
    private DbManager manager;

    public MongoController(DbManager manager) {
        this.manager = manager;
    }

    public void updateOrCreateClient(String principal, Client client) throws NotFoundParameterException, IllegalAccessException {
        Datastore datastore = manager.getDatastore();
        Query<Client> query = datastore
                .createQuery(Client.class)
                .field("principal")
                .equal(principal);

        if (client == null) {
            client = new Client();
        } else if (client.getPrincipal() == null) {
            client.setPrincipal(principal);
        } else if (!client.getPrincipal().equals(principal)) {
            throw new IllegalAccessException("Client has wrong principal");
        }

        datastore.updateFirst(query, client, true);

        if (client.getId() == null) {
            DBCursor dbObjects = datastore.getCollection(Client.class).find(query.getQueryObject());
            if (dbObjects.size() != 1) {
                throw new IllegalStateException("Two client with one principal");
            } else {
                Map map = dbObjects.one().toMap();
                ObjectId id = (ObjectId) map.get("_id");
                client.setId(id);
            }
        }

        //handle added queries
        handleAddedQueriesChange(datastore, client);
        //handle removed queries
        handleRemovedQueriesChange(datastore, client);
    }

    public Client getClientByPrincipal(String principal) throws NotFoundRegisteredClientException {
        Datastore datastore = manager.getDatastore();
        List<Client> clients = datastore
                .createQuery(Client.class)
                .field("principal")
                .equal(principal).asList();

        if (clients != null && clients.size() != 1) {
            throw new NotFoundRegisteredClientException();
        }

        return clients.get(0);
    }

    public class NotFoundRegisteredClientException extends Exception{

    }
}
