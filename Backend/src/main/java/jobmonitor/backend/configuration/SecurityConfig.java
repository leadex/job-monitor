package jobmonitor.backend.configuration;

import jobmonitor.backend.oauth.OAuth2AutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Maxim Ivanov.
 */
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2AutoConfiguration oAuth2AutoConfiguration;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
                .addFilterBefore(oAuth2AutoConfiguration.getOAuthFilter(), BasicAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/", "/login/**", "/webjars/**").permitAll()
                .antMatchers("/js/**", "/view/**", "/images/**").permitAll()
                .anyRequest().authenticated().and()
            .formLogin()
                .loginPage("/").and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).permitAll().and()
            .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication(); // TODO: Change In memory to persistence
    }
}
