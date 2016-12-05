package jobmonitor.backend.oauth;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

/**
 * Created by Maxim Ivanov.
 */
public abstract class OAuthPrincipalExtractor implements PrincipalExtractor {

    private final String providerName;

    public OAuthPrincipalExtractor(String provider) {
        providerName = provider;
    }

    public String getProviderName() {
        return providerName;
    }
}
