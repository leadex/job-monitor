package ru.leadexsystems.esb.jobmonitor.slack.processor;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.leadexsystems.esb.jobmonitor.slack.model.JobTemplate;
import ru.leadexsystems.startup.jobmonitor.common.model.Notification;
import ru.leadexsystems.startup.jobmonitor.common.model.Slack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alexar on 24.11.2016.
 */
public class AggregateJobProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Notification body = exchange.getIn().getBody(Notification.class);

        List<JobTemplate> res = body.getJobs().stream().map(x -> {
            JobTemplate jobTemplate = new JobTemplate();
            jobTemplate.setJob(x);

            Gson gson = new Gson();
            jobTemplate.setSlack(gson.fromJson(body.getParams(), Slack.class));

            return jobTemplate;
        }).collect(Collectors.toList());

        exchange.getOut().setBody(res);
    }

}
