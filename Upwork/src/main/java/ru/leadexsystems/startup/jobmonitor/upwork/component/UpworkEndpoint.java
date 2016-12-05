package ru.leadexsystems.startup.jobmonitor.upwork.component;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;
import ru.leadexsystems.startup.jobmonitor.common.util.DateManager;

/**
 * Created by alexar.
 */
public class UpworkEndpoint extends DefaultEndpoint {

    @UriParam(name = "key")
    private String key;

    @UriParam(name = "secret")
    private String secret;

    @UriParam(name = "token")
    private String token;

    @UriParam(name = "tokenSecret")
    private String tokenSecret;

    @UriParam(name = "start")
    private int start;

    @UriParam(name = "limit")
    private int limit;

    DateManager dateManager;

    public UpworkEndpoint(DateManager dateManager) {
        super();
        this.dateManager = dateManager;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new UpworkProducer(this, key, secret, token, tokenSecret, start, limit, dateManager);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new IllegalStateException("Consumer not allowed in this component");
    }

    @Override
    protected String createEndpointUri() {
        return "upwork://camel";
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
