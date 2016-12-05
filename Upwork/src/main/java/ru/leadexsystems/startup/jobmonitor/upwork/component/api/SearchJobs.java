package ru.leadexsystems.startup.jobmonitor.upwork.component.api;

import com.Upwork.api.OAuthClient;
import com.Upwork.api.Routers.Jobs.Search;
import org.json.JSONException;
import org.json.JSONObject;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexar.
 */
public class SearchJobs {

    private final OAuthClient client;
    private int start;
    private int limit;

    public SearchJobs(ClientBuilder builder, int start, int limit) {
        this.client = builder.createClient();
        this.start = start;
        this.limit = limit;
    }

    public List<Job> byKeyword(String keyword) throws JSONException {
        Search jobs = new Search(client);
        HashMap<String, String> params = new HashMap<>();
        params.put("q", keyword);
        params.put("paging", start + ";" + limit);
        JSONObject json = jobs.find(params);
        if (json != null && !json.has("jobs")) {
            System.out.println("--------------------------------------------------");
            System.out.println(json.toString());
            System.out.println("--------------------------------------------------");
            return new ArrayList<Job>();
        }
        return Converter.convertToListJob(json.getJSONArray("jobs"));
    }
}
