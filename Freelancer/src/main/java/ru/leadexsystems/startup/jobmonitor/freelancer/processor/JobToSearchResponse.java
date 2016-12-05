package ru.leadexsystems.startup.jobmonitor.freelancer.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.common.model.SearchResponse;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;

import java.util.List;

/**
 * Created by ekabardinsky on 11/18/16.
 */
public class JobToSearchResponse implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<Job> body = exchange.getIn().getBody(List.class);
        Task query = (Task) exchange.getProperty("query");

        SearchResponse response = new SearchResponse();
        response.setJobs(body);
        response.setQuery(query.getQuery());
        response.setSource(JobSource.freelancer);

        exchange.getOut().setBody(response);
    }
}
