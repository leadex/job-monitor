package ru.leadexsystems.startup.jobmonitor.common.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Query {

    @SerializedName("upwork")
    @Expose
    private List<String> upwork = new ArrayList<String>();
    @SerializedName("freelancer")
    @Expose
    private List<String> freelancer = new ArrayList<String>();
    @SerializedName("guru")
    @Expose
    private List<String> guru = new ArrayList<String>();

    /**
     * @return The upwork
     */
    public List<String> getUpwork() {
        return upwork;
    }

    /**
     * @param upwork The upwork
     */
    public void setUpwork(List<String> upwork) {
        this.upwork = upwork;
    }

    /**
     * @return The freelancer
     */
    public List<String> getFreelancer() {
        return freelancer;
    }

    /**
     * @param freelancer The freelancer
     */
    public void setFreelancer(List<String> freelancer) {
        this.freelancer = freelancer;
    }

    /**
     * @return The guru
     */
    public List<String> getGuru() {
        return guru;
    }

    /**
     * @param guru The guru
     */
    public void setGuru(List<String> guru) {
        this.guru = guru;
    }

}