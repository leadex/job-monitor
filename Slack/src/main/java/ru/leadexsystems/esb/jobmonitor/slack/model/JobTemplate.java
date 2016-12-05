package ru.leadexsystems.esb.jobmonitor.slack.model;

import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.Notification;
import ru.leadexsystems.startup.jobmonitor.common.model.Slack;

/**
 * Created by alexar on 24.11.2016.
 */
public class JobTemplate {

    private Job job;
    private Slack slack;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Slack getSlack() {
        return slack;
    }

    public void setSlack(Slack slack) {
        this.slack = slack;
    }
}
