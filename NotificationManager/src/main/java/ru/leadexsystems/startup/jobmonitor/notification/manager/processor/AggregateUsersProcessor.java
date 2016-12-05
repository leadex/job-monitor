package ru.leadexsystems.startup.jobmonitor.notification.manager.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.model.Client;
import ru.leadexsystems.startup.jobmonitor.common.model.SearchResponse;
import ru.leadexsystems.startup.jobmonitor.common.util.DbManager;
import ru.leadexsystems.startup.jobmonitor.notification.manager.model.NotificationTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ekabardinsky on 11/21/16.
 */
public class AggregateUsersProcessor implements Processor {
    private DbManager manager;

    public AggregateUsersProcessor(DbManager manager) {
        this.manager = manager;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        SearchResponse body = exchange.getIn().getBody(SearchResponse.class);
        List<Client> clientList = manager.getClientsByQuery(body.getQuery(), body.getSource());

        List<NotificationTemplate> templates = clientList.stream().map(x -> {
            NotificationTemplate template = new NotificationTemplate();
            template.setResponse(body);
            template.setClient(x);
            return template;
        }).collect(Collectors.toList());

        exchange.getOut().setBody(templates);
    }

}
