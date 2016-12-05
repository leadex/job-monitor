package ru.leadexsystems.esb.jobmonitor.slack.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.slack.helper.SlackMessage;
import org.springframework.beans.factory.annotation.Value;
import ru.leadexsystems.esb.jobmonitor.slack.model.JobTemplate;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;
import ru.leadexsystems.startup.jobmonitor.common.model.JobSource;
import ru.leadexsystems.startup.jobmonitor.common.model.Slack;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by ekabardinsky on 10/27/16.
 */
public class JobToSlackMessageProcessor implements Processor {
    @Value("${upwork.iconImageUrl}")
    private String upworkIconImageUrl;

    @Value("${freelancer.iconImageUrl}")
    private String freelancerIconImageUrl;

    @Value("${guru.iconImageUrl}")
    private String guruIconImageUrl;

    @Override
    public void process(Exchange exchange) throws Exception {
        JobTemplate jobTemplate = exchange.getIn().getBody(JobTemplate.class);
        Job job = jobTemplate.getJob();

        SlackMessage message = new SlackMessage();
        SlackMessage.Attachment attachment = message.new Attachment();

        if (job != null) {
            //init lists
            List<SlackMessage.Attachment> attachmentList = new ArrayList<>();
            List<SlackMessage.Attachment.Field> fields = new ArrayList<>();
            attachment.setFields(fields);
            attachmentList.add(attachment);
            message.setAttachments(attachmentList);

            //map job to attach
            ifNotNull(job.getTitle(), (value) -> attachment.setTitle(value));
            ifNotNull(job.getTitle(), (value) -> attachment.setFallback(value));
            ifNotNull(job.getUrl(), (value) -> attachment.setTitleLink(value));
            ifNotNull(job.getSnippet(), (value) -> {
                SlackMessage.Attachment.Field field = attachment.new Field();
                field.setTitle("Description");
                field.setValue(value);
                fields.add(field);
            });
            ifNotNull(job.getBudget(), (value) -> {
                if (!"0".equals(value)) {
                    SlackMessage.Attachment.Field field = attachment.new Field();
                    field.setTitle("Budget");
                    field.setValue(value);
                    fields.add(field);
                }
            });
            ifNotNull(job.getRegion(), (value) -> {
                SlackMessage.Attachment.Field field = attachment.new Field();
                field.setTitle("Region");
                field.setValue(value);
                fields.add(field);
            });
            ifNotNull(job.getCreateDate(), (value) -> {
                attachment.setTs(value.toInstant().getEpochSecond());
            });

            ifNotNull(job.getJobSource(), (value) -> {
                attachment.setFooterIcon(getFooterImage(value));
                attachment.setFooter(value.toString());
            });
        }

        Slack slack = jobTemplate.getSlack();

        String uri = URLEncoder.encode(slack.getChannel(), "UTF-8") +
                "?webhookUrl=" + URLEncoder.encode(slack.getWebHook(), "UTF-8") +
                "&username=" + URLEncoder.encode(slack.getBotName(), "UTF-8") +
                "&iconEmoji=" + URLEncoder.encode(slack.getIconEmoji(), "UTF-8");

        exchange.getOut().setHeader("uri", uri);
        exchange.getOut().setBody(message);
    }

    private static <T> void ifNotNull(T value, Consumer<T> runnable) {
        if (value != null && !"".equals(value.toString())) {
            runnable.accept(value);
        }
    }

    private String getFooterImage(JobSource source) {
        switch (source) {
            case upwork: return upworkIconImageUrl;
            case freelancer: return freelancerIconImageUrl;
            case guru: return guruIconImageUrl;
            default: return "";
        }
    }
}
