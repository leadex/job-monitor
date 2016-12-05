package ru.leadexsystems.startup.jobmonitor.freelancer.route;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.filter.AcceptFilledCollectionFilter;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.freelancer.processor.JobToSearchResponse;

/**
 * Created by ekabardinsky on 10/25/16.
 */
@Component
public class FreelancerJobMonitorMainRoute extends RouteBuilder {

    @Autowired
    private PojoToJsonProcessor pojoToJsonProcessor;

    @Autowired
    private JsonToPojoProcessor taskToPojoProcessor;

    @Autowired
    private JobToSearchResponse jobToSearchResponse;

    @Value("${freelancer.camelCron}")
    private String cron;

    @Value("${freelancer.apiSearchUrl}")
    private String apiSearchUrl;

    @Value("${freelancer.pagination.start}")
    private String start;

    @Value("${freelancer.pagination.limit}")
    private String limit;

    @Value("${activemq.taskQueue}")
    private String taskQueue;

    @Value("${activemq.responseQueue}")
    private String responseQueue;

    @Value("${activemq.user}")
    private String activemqUser;

    @Value("${activemq.password}")
    private String activemqPass;

    @Override
    public void configure() throws Exception {
        //listening task queue
        from(activeMQ(taskQueue))
                .to("direct:pullFreelancer");

        //pull from freelancer
        from("direct:pullFreelancer")
                .process(taskToPojoProcessor)
                .setProperty("query", body())
                .process(taskToQuery())
                .to(freelancer())
                .filter().method(AcceptFilledCollectionFilter.class, "isFilledResponse")
                .process(jobToSearchResponse)
                .to("direct:output");

        //send message to response queue
        from("direct:output")
                .process(pojoToJsonProcessor)
                .to(activeMQ(responseQueue));

    }

    private String activeMQ(String queue) {
        return "activemq:queue://" + queue +
                "?username=" + activemqUser +
                "&password=" + activemqPass +
                "&disableReplyTo=true";
    }

    private String freelancer() {
        return "freelancer://something" +
                "?start=" + start +
                "&limit=" + limit +
                "&apiSearchUrl=" + apiSearchUrl;
    }

    private Processor taskToQuery() {
        return (exchange -> {
            Task body = exchange.getIn().getBody(Task.class);
            exchange.getOut().setBody(body.getQuery());
        });
    }
}
