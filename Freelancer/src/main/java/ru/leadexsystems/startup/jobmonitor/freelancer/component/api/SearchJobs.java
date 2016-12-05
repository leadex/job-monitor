package ru.leadexsystems.startup.jobmonitor.freelancer.component.api;

import com.Upwork.api.OAuthClient;
import com.Upwork.api.Routers.Jobs.Search;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.freelancer.component.api.model.FreelancerProject;
import ru.leadexsystems.startup.jobmonitor.freelancer.component.api.model.FreelancerResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexar.
 */
public class SearchJobs {

    private int start;
    private int limit;
    private String apiSearchUrl;

    public SearchJobs(String apiSearchUrl, int start, int limit) {
        this.start = start;
        this.limit = limit;
        this.apiSearchUrl = apiSearchUrl;
    }

    public List<Job> byKeyword(String keyword) throws Exception {
        return getJobsFromQuery(getQueryUrl(keyword));
    }

    private String getQueryUrl(String keyword) {
        String answer = apiSearchUrl +
                "?query=" + keyword +
                "&limit=" + limit +
                "&sort_field=submitdate";
        return answer;
    }

    private List<Job> getJobsFromQuery(String query) throws IOException {
        //prepare http client
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager()
                .getParams()
                .setConnectionTimeout(5000);

        //prepare method
        GetMethod getMethod = new GetMethod(query.replace(" ", "%20"));
        getMethod.setFollowRedirects(true);

        //execute
        httpClient.executeMethod(getMethod);
        String responseBodyAsString = getMethod.getResponseBodyAsString();

        //Deserialize
        Gson gson = new GsonBuilder().create();
        FreelancerResult result = gson.fromJson(responseBodyAsString, FreelancerResult.class);
        return Converter.convertToListJob(result);
    }


}
