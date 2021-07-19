package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .antMatcher("/**").authorizeRequests()
            .antMatchers("/viewers/auth", "/ti/auth").authenticated()
            .anyRequest().permitAll()
            .and()
            .oauth2Login();
    }


    
}
