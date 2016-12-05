package jobmonitor.backend.oauth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxim Ivanov.
 */
@Component
public class OAuth2AutoConfiguration {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    OAuthProviders oAuthProviders;

    public Filter getOAuthFilter() {
        CompositeFilter filter = new CompositeFilter();

        List<Filter> filters = generateFilters();

        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(OAuthProvider provider, String redirectUri, String path) {
        provider.getClient().setPreEstablishedRedirectUri(redirectUri + path);

        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(provider.getClient(), oauth2ClientContext);
        OAuthPrincipalExtractor principalExtractor = null;

        try {
            if (provider.getPrincipalExtractor() != null) {
                String principalExtractorClassName = this.getClass().getPackage().getName()
                    + "."
                    + provider.getPrincipalExtractor();

                Class<? extends OAuthPrincipalExtractor> clazz =
                    (Class<? extends OAuthPrincipalExtractor>) Class.forName(principalExtractorClassName);
                Constructor<? extends OAuthPrincipalExtractor> ctor = clazz.getConstructor(String.class);
                principalExtractor = ctor.newInstance(new Object[] { provider.getName() });
            }
        }
        catch (ClassNotFoundException|NoSuchMethodException
            |IllegalAccessException|InstantiationException|InvocationTargetException e) {
            e.printStackTrace();
        }

        if (principalExtractor == null) {
            principalExtractor = new OAuthCommonPrincipalExtractor(provider.getName());
        }

        // User info and token extractor
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(provider.getResource().getUserInfoUri(),
            provider.getClient().getClientId());

        tokenServices.setPrincipalExtractor(principalExtractor);
        tokenServices.setRestTemplate(oAuth2RestTemplate);

        // Auth http filter
        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter =
            new OAuth2ClientAuthenticationProcessingFilter(path);

        oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
        oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
        oAuth2ClientAuthenticationFilter.setAuthenticationSuccessHandler(successHandler());
        return oAuth2ClientAuthenticationFilter;
    }

    private List<Filter> generateFilters() {
        return oAuthProviders.providers.stream()
            .map(provider -> ssoFilter(provider, oAuthProviders.getRedirectUri(), "/login/" + provider.getName()))
            .collect(Collectors.toList());
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> response.sendRedirect("/#/manage");
    }
}
