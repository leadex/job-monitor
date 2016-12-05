package ru.leadexsystems.startup.jobmonitor.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Алексей on 09.11.2016.
 */
public class Condition {

    @SerializedName("name")
    private String name;

    @SerializedName("values")
    private List<String> values;

    public String getNameField() {
        return name;
    }

    public void setNameField(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }
}
