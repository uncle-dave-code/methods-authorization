package com.uncledavecode.methodsauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                  //PreAuthorize, PostAuthorize, PreFilter, PostFilter
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var adminUser = User.withUsername("uncledave")
                .password(passwordEncoder().encode("UncleDave123"))
                .authorities("write")
                .build();
        var user = User.withUsername("user")
                .password(passwordEncoder().encode("User123"))
                .authorities("read")
                .build();

        var result = new InMemoryUserDetailsManager();
        result.createUser(adminUser);
        result.createUser(user);

        return result;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
