package ru.leadexsystems.startup.jobmonitor.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Generated("org.jsonschema2pojo")
@Entity("searchRequests")
public class SearchRequest {
    @Id
    private ObjectId id;

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("source")
    @Expose
    private JobSource source;
    @SerializedName("client")
    @Expose
    private List<String> client = new ArrayList<String>();
    @SerializedName("date")
    @Expose
    private Date date;

    /**
     * @return The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return The source
     */
    public JobSource getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(JobSource source) {
        this.source = source;
    }

    /**
     * @return The client
     */
    public List<String> getClient() {
        return client;
    }

    /**
     * @param client The client
     */
    public void setClient(List<String> client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}