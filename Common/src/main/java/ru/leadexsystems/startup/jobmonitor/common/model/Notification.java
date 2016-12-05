
package ru.leadexsystems.startup.jobmonitor.common.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bson.Document;

@Generated("org.jsonschema2pojo")
public class Notification {

    @SerializedName("notificationType")
    @Expose
    private NotificationType notificationType;
    @SerializedName("jobs")
    @Expose
    private List<Job> jobs = new ArrayList<Job>();
    @SerializedName("params")
    @Expose
    private String params;


    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * @return The job
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     * @param job The job
     */
    public void setJobs(List<Job> job) {
        this.jobs = job;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
