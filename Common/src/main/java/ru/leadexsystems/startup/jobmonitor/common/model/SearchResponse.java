package ru.leadexsystems.startup.jobmonitor.common.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SearchResponse {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("source")
    @Expose
    private JobSource source;
    @SerializedName("jobs")
    @Expose
    private List<Job> jobs = new ArrayList<Job>();

    /**
     *
     * @return
     * The query
     */
    public String getQuery() {
        return query;
    }

    /**
     *
     * @param query
     * The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     *
     * @return
     * The job
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     *
     * @param job
     * The job
     */
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public JobSource getSource() {
        return source;
    }

    public void setSource(JobSource source) {
        this.source = source;
    }
}