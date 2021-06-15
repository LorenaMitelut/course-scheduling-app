package com.endava.courseschedulingapp.config;

import com.endava.courseschedulingapp.entities.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/users/trainer").hasRole(UserRole.ADMIN.getRole())
                .mvcMatchers(HttpMethod.POST, "/users/client").permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/users").hasRole(UserRole.ADMIN.getRole())
                .mvcMatchers(HttpMethod.DELETE, "/courses").hasRole(UserRole.ADMIN.getRole())
                .mvcMatchers(HttpMethod.POST, "/courses").hasRole(UserRole.ADMIN.getRole())
                .mvcMatchers(HttpMethod.PUT, "/courses/**").hasRole(UserRole.TRAINER.getRole())
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                .anyRequest().authenticated();

        http.csrf().disable();
    }
}
