package com.khripko.qrokapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by Boris on 21.08.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private @Value("${spring.security.user.name}") String user;
    private @Value("${spring.security.user.password}") String userPass;
    private @Value("${spring.security.guest.name}") String guest;
    private @Value("${spring.security.guest.password}") String guestPass;
    private @Value("${spring.security.admin.name}") String admin;
    private @Value("${spring.security.admin.password}") String adminPass;
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String GUEST = "GUEST";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(user).password(userPass).roles("USER")
                .and().withUser(guest).password(guestPass).roles("GUEST")
                .and().withUser(admin).password(adminPass).roles("ADMIN");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api*//**").hasAnyRole(ADMIN, USER, GUEST)
                .antMatchers(HttpMethod.POST, "/api*//**").hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, "/api*//**").hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, "/api*//**").hasRole(ADMIN)
                .anyRequest().permitAll()
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
