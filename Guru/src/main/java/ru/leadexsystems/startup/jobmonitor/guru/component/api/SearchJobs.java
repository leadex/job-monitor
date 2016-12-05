package ru.leadexsystems.startup.jobmonitor.guru.component.api;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

import java.net.URL;
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
        return apiSearchUrl + "/" + keyword;
    }

    private List<Job> getJobsFromQuery(String query) throws Exception {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(query)));
        return Converter.convertToListJob(feed);
    }

}
