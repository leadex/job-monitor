package jobmonitor.backend.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * Created by Maxim Ivanov.
 */
public class OAuthProvider {
    private String name;
    private String principalExtractor;

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public String getName() {
        return name;
    }
    public void setName(String value) {
        name = value;
    }

    public String getPrincipalExtractor() {
        return principalExtractor;
    }

    public void setPrincipalExtractor(String principalExtractor) {
        this.principalExtractor = principalExtractor;
    }

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
