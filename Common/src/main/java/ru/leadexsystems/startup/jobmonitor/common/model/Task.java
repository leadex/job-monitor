package ru.leadexsystems.startup.jobmonitor.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ekabardinsky on 11/18/16.
 */
public class Task {
    @SerializedName("query")
    @Expose
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
