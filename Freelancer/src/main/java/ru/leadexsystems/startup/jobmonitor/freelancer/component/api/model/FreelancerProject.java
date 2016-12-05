package ru.leadexsystems.startup.jobmonitor.freelancer.component.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Алексей on 21.11.2016.
 */

@Generated("org.jsonschema2pojo")
public class FreelancerProject {

    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("preview_description")
    @Expose
    private String previewDescription;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("submitdate")
    @Expose
    private Integer submitdate;
    @SerializedName("nonpublic")
    @Expose
    private Boolean nonpublic;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @Expose
    private String language;
    @SerializedName("seo_url")
    @Expose
    private String seoUrl;
    @SerializedName("urgent")
    @Expose
    private Boolean urgent;
    @SerializedName("time_submitted")
    @Expose
    private Integer timeSubmitted;
    @SerializedName("budget")
    @Expose
    private Budget budget;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getPreviewDescription() {
        return previewDescription;
    }

    public void setPreviewDescription(String previewDescription) {
        this.previewDescription = previewDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(Integer submitdate) {
        this.submitdate = submitdate;
    }

    public Boolean getNonpublic() {
        return nonpublic;
    }

    public void setNonpublic(Boolean nonpublic) {
        this.nonpublic = nonpublic;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public void setSeoUrl(String seoUrl) {
        this.seoUrl = seoUrl;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public Integer getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Integer timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }




/* ******************************* */

    @Generated("org.jsonschema2pojo")
    public class Location {

        @SerializedName("country")
        @Expose
        private Country country;

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }
    }

    @Generated("org.jsonschema2pojo")
    public class Country {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Generated("org.jsonschema2pojo")
    public class Budget {

        @SerializedName("minimum")
        @Expose
        private Integer minimum;
        @SerializedName("maximum")
        @Expose
        private Integer maximum;

        public Integer getMinimum() {
            return minimum;
        }

        public void setMinimum(Integer minimum) {
            this.minimum = minimum;
        }

        public Integer getMaximum() {
            return maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }
    }
    @Generated("org.jsonschema2pojo")
    public class Currency {

        @SerializedName("code")
        @Expose
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
