package ru.leadexsystems.startup.jobmonitor.common.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.beanclass.ClassComponent;
import ru.leadexsystems.startup.jobmonitor.common.model.Job;

/**
 * Created by ekabardinsky on 11/1/16.
 */
public class PojoToJsonProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Object body = exchange.getIn().getBody();
        if (body != null) {
            Gson gson = new GsonBuilder().create();
            String serializedBody = gson.toJson(body);
            exchange.getOut().setBody(serializedBody);
        }
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setAttachments(exchange.getIn().getAttachments());
    }
}
