package ru.leadexsystems.startup.jobmonitor.guru.route;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.startup.jobmonitor.common.filter.AcceptFilledCollectionFilter;
import ru.leadexsystems.startup.jobmonitor.common.model.Task;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;
import ru.leadexsystems.startup.jobmonitor.common.processor.PojoToJsonProcessor;
import ru.leadexsystems.startup.jobmonitor.guru.processor.JobToSearchResponse;

/**
 * Created by ekabardinsky on 10/25/16.
 */
@Component
public class GuruJobMonitorMainRoute extends RouteBuilder {

    @Autowired
    private PojoToJsonProcessor pojoToJsonProcessor;

    @Autowired
    private JsonToPojoProcessor taskToPojoProcessor;

    @Autowired
    private JobToSearchResponse jobToSearchResponse;

    @Value("${guru.camelCron}")
    private String cron;

    @Value("${guru.apiSearchUrl}")
    private String apiSearchUrl;

    @Value("${guru.pagination.start}")
    private String start;

    @Value("${guru.pagination.limit}")
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
                .to("direct:pullGuru");

        //pull from guru
        from("direct:pullGuru")
                .process(taskToPojoProcessor)
                .setProperty("query", body())
                .process(taskToQuery())
                .to(guru())
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

    private String guru() {
        return "guru://something" +
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
