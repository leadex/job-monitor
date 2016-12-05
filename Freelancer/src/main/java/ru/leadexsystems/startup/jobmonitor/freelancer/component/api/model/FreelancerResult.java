package ru.leadexsystems.startup.jobmonitor.freelancer.component.api.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

/**
 * Created by Алексей on 21.11.2016.
 */
@Generated("org.jsonschema2pojo")
public class FreelancerResult {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private Result result;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The result
     */
    public Result getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    public List<FreelancerProject> getProjects() {
        return result.getProjects();
    }

    public class Result {

        @SerializedName("total_count")
        @Expose
        private Integer totalCount;
        @SerializedName("selected_bids")
        @Expose
        private Object selectedBids;
        @SerializedName("users")
        @Expose
        private Object users;
        @SerializedName("projects")
        @Expose
        private List<FreelancerProject> projects;

        /**
         *
         * @return
         * The totalCount
         */
        public Integer getTotalCount() {
            return totalCount;
        }

        /**
         *
         * @param totalCount
         * The total_count
         */
        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        /**
         *
         * @return
         * The selectedBids
         */
        public Object getSelectedBids() {
            return selectedBids;
        }

        /**
         *
         * @param selectedBids
         * The selected_bids
         */
        public void setSelectedBids(Object selectedBids) {
            this.selectedBids = selectedBids;
        }

        /**
         *
         * @return
         * The users
         */
        public Object getUsers() {
            return users;
        }

        /**
         *
         * @param users
         * The users
         */
        public void setUsers(Object users) {
            this.users = users;
        }

               public List<FreelancerProject> getProjects() {
            return projects;
        }

        public void setProjects(List<FreelancerProject> projects) {
            this.projects = projects;
        }
    }

}