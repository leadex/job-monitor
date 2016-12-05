package ru.leadexsystems.startup.jobmonitor.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.Date;
import java.util.List;

/**
 * Created by ekabardinsky on 10/26/16.
 */
@Generated("org.jsonschema2pojo")
public class Job {
    @SerializedName("snippet")
    @Expose
    private String snippet;

    @SerializedName("jobType")
    @Expose
    private String jobType;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("createDate")
    @Expose
    private Date createDate;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("skills")
    @Expose
    private List<String> skills;

    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("jobStatus")
    @Expose
    private String jobStatus;

    @SerializedName("clientInfo")
    @Expose
    private String clientInfo;

    @SerializedName("budget")
    @Expose
    private String budget;

    @SerializedName("jobSource")
    @Expose
    private JobSource jobSource;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("region")
    @Expose
    private String region;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public JobSource getJobSource() {
        return jobSource;
    }

    public void setJobSource(JobSource jobSource) {
        this.jobSource = jobSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Object getValue(String name) {
        //TODO migrate to use reflection
        switch (name) {
            case "snippet":
                return getSnippet();
            case "jobType":
                return getJobType();
            case "category":
                return getCategory();
            case "createDate":
                return getCreateDate();
            case "title":
                return getTitle();
            case "url":
                return getUrl();
            case "duration":
                return getDuration();
            case "jobStatus":
                return getJobStatus();
            case "clientInfo":
                return getClientInfo();
            case "budget":
                return getBudget();
            case "id":
                return getId();
            case "region":
                return getRegion();
            default:
                break;
        }
        return null;
    }
}
