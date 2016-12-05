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
public class JsonToPojoProcessor implements Processor {

    private Class serializedClass;

    public JsonToPojoProcessor(Class serializedClass) {
        this.serializedClass = serializedClass;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        if (body != null) {
            Gson gson = new GsonBuilder().create();
            Object pojo = gson.fromJson(body, serializedClass);
            exchange.getOut().setBody(pojo);
        }
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setAttachments(exchange.getIn().getAttachments());
    }
}
