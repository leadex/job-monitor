package ru.leadexsystems.esb.jobmonitor.slack.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leadexsystems.esb.jobmonitor.slack.processor.AggregateJobProcessor;
import ru.leadexsystems.esb.jobmonitor.slack.processor.JobToSlackMessageProcessor;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.common.processor.JsonToPojoProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ekabardinsky on 11/1/16.
 */
@Component
public class SlackMainRoute extends RouteBuilder {
    @Autowired
    private JobToSlackMessageProcessor jobToSlackMessageProcessor;

    @Autowired
    private JsonToPojoProcessor jsonToPojoProcessor;

    @Autowired
    private AggregateJobProcessor aggregateJobProcessor;

    @Value("${activemq.slackNoteQueue}")
    private String activemqQueue;

    @Value("${activemq.user}")
    private String activemqUser;

    @Value("${activemq.password}")
    private String activemqPass;

    @Override
    public void configure() throws Exception {

        from("activemq:queue://" + activemqQueue +
                "?username=" + activemqUser +
                "&password=" + activemqPass +
                "&disableReplyTo=true")
                .process(jsonToPojoProcessor)
                .process(aggregateJobProcessor)
                .split(body())
                .process(jobToSlackMessageProcessor)
                .to("direct:slackOutput");

        from("direct:slackOutput")
                .recipientList(simple("slack:#${header.uri}"));

    }

}
