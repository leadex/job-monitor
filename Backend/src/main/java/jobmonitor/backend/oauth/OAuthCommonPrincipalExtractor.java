package jobmonitor.backend.oauth;

import java.util.Map;

/**
 * Created by Maxim Ivanov.
 */
public class OAuthCommonPrincipalExtractor extends OAuthPrincipalExtractor {
    private static final String[] PRINCIPAL_KEYS = new String[]{"user", "username", "userid", "user_id", "login", "id", "name"};

    public OAuthCommonPrincipalExtractor(String providerName) {
        super(providerName);
    }

    public Object extractPrincipal(Map<String, Object> map) {
        String userId = null;

        for(int i = 0; i < PRINCIPAL_KEYS.length; ++i) {
            String key = PRINCIPAL_KEYS[i];
            if(map.containsKey(key)) {
                userId = map.get(key).toString();
                break;
            }
        }

        if (userId != null) {
            userId = getProviderName() + "#" + userId;
        }

        return userId;
    }
}
