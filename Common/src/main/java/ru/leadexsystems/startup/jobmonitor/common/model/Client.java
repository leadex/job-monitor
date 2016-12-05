package ru.leadexsystems.startup.jobmonitor.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Entity("clients")
public class Client {
    @Id
    @SerializedName("_id")
    private ObjectId id;

    @SerializedName("principal")
    @Expose
    private String principal;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("notification")
    @Expose
    private NotificationParams notification;

    /**
     * @return The id
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * @return The principal
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * @param principal The principal
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * @return The query
     */
    public Query getQuery() {
        return query;
    }

    /**
     * @param query The query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

    /**
     * @return The notification
     */
    public NotificationParams getNotification() {
        return notification;
    }

    public void setNotification(NotificationParams notification) {
        this.notification = notification;
    }
}