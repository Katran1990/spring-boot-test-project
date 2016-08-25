package com.khripko.qrokapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(user).password(userPass).roles("USER")
                .and().withUser(guest).password(guestPass).roles("GUEST")
                .and().withUser(admin).password(adminPass).roles("ADMIN");
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable().antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("ADMIN")
                    .and().httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/")
                    .and().logout().logoutUrl("/logout").permitAll().logoutSuccessUrl("/login?logout");

        }

    }

}