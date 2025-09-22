package com.example.EmployeeMvc.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration

public class projectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/customLogin", "/access-denied", "/perform_login", "/css/**", "/js/**", "/images/**").permitAll()
                        // admin-only areas (manage users/roles)
                        .requestMatchers(HttpMethod.GET, "/employees").hasAnyRole("ADMIN", "MANAGER", "HR", "USER", "GUEST")
                        // Allow updating employees
                        .requestMatchers(HttpMethod.POST, "/employee/**").authenticated()

                        // Only Admin can delete employees
                        .requestMatchers(HttpMethod.DELETE, "/employee/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/employee/**").hasRole("ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/customLogin")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/employees", true)
                        .permitAll()
                )
                // if user is authenticated but not authorized for a URL -> forward to this page
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
