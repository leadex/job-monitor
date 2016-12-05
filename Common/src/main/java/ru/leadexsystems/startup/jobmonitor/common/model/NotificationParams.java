package ru.leadexsystems.startup.jobmonitor.common.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Generated("org.jsonschema2pojo")
public class NotificationParams {
    @SerializedName("slack")
    @Expose
    private Slack slack;
    /**
     *
     * @return
     * The slack
     */
    public Slack getSlack() {
        return slack;
    }
    /**
     *
     * @param slack
     * The slack
     */
    public void setSlack(Slack slack) {
        this.slack = slack;
    }
}