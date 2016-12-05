package jobmonitor.backend.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim Ivanov.
 */
@ConfigurationProperties(prefix = "oauth2-conf")
@Component
public class OAuthProviders {
    String redirectUri;

    @NestedConfigurationProperty
    List<OAuthProvider> providers = new ArrayList<>();

    public List<OAuthProvider> getProviders() {
        return providers;
    }

    public void setProviders(List<OAuthProvider> providers) {
        this.providers = providers;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
