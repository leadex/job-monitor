package ru.leadexsystems.startup.jobmonitor.common.filter;

import org.apache.camel.Exchange;

import java.util.List;

/**
 * Created by ekabardinsky on 11/23/16.
 */
public class AcceptFilledCollectionFilter {
    public boolean isFilledResponse(Exchange exchange) {
        Object body = exchange.getIn().getBody();
        if (body != null && body instanceof List) {
            List response = ((List) body);
            return response.size() != 0;
        } else {
            throw new IllegalStateException("Body should be: not null and instance of List.class");
        }

    }
}
