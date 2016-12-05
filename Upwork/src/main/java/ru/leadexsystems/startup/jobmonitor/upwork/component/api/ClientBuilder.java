package ru.leadexsystems.startup.jobmonitor.upwork.component.api;

import com.Upwork.api.OAuthClient;
import com.Upwork.api.Config;
import ru.leadexsystems.startup.jobmonitor.common.exception.NotFoundParameterException;
import ru.leadexsystems.startup.jobmonitor.common.util.DateManager;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by alexar.
 */
public class ClientBuilder {

    private String key, secret, token, tokenSecret;

    public ClientBuilder(String key, String secret, String token, String tokenSecret, DateManager parameters) {
        this.key = key;
        this.secret = secret;
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public OAuthClient createClient(){
        Properties keys = new Properties();
        keys.setProperty("consumerKey", key);
        keys.setProperty("consumerSecret", secret);
        Config config = new Config(keys);
        OAuthClient client = new OAuthClient(config);
        String authzUrl;

        // authorize application and get access token
        if (token == null && tokenSecret == null) {
            Scanner scanner = new Scanner(System.in);
            authzUrl = client.getAuthorizationUrl();

            System.out.println("1. Copy paste the following url in your browser : ");
            System.out.println(authzUrl);
            System.out.println("2. Grant access ");
            System.out.println("3. Copy paste the oauth_verifier parameter here :");

            String oauth_verifier = scanner.nextLine();

            String verifier = null;
            try {
                verifier = URLDecoder.decode(oauth_verifier,"UTF-8");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            HashMap<String, String> accessTokenSet = client.getAccessTokenSet(verifier);
            scanner.close();
        } else {
            // set known access token-secret pair
            client.setTokenWithSecret(token, tokenSecret);
        }
        return client;
    }
}
