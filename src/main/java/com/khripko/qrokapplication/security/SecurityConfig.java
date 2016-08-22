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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.name}")
    private String user;
    @Value("${spring.security.user.password}")
    private String userPass;
    @Value("${spring.security.guest.name}")
    private String guest;
    @Value("${spring.security.guest.password}")
    private String guestPass;
    @Value("${spring.security.admin.name}")
    private String admin;
    @Value("${spring.security.admin.password}")
    private String adminPass;

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String GUEST = "GUEST";
    private static final String URL = "/api*//**";

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
                .antMatchers(HttpMethod.GET, URL).hasAnyRole(ADMIN, USER, GUEST)
                .antMatchers(HttpMethod.POST, URL).hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, URL).hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, URL).hasRole(ADMIN)
                .anyRequest().permitAll()
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
