package com.example.observability.infrastructure.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.OPTIONS;

@EnableWebSecurity
@Configuration
public class ConfigSecurity {

    @Value("${api.security.user}")
    private String userApi;

    @Value("${api.security.password}")
    private String passwordApi;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username(userApi).password(passwordApi).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }

}
