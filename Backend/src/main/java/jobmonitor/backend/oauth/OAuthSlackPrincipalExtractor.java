package jobmonitor.backend.oauth;

import java.util.Map;

/**
 * Created by Maxim Ivanov.
 */
public class OAuthSlackPrincipalExtractor extends OAuthPrincipalExtractor {
    public OAuthSlackPrincipalExtractor(String providerName) {
        super(providerName);
    }

    public Object extractPrincipal(Map<String, Object> map) {
        String userId = getProviderName()
            + "#"
            + ((Map<String, Object>)map.get("team")).get("id").toString()
            + "#"
            + ((Map<String, Object>)map.get("user")).get("id").toString();

        return userId;
    }
}
