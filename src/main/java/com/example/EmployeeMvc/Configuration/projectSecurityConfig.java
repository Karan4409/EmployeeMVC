package com.example.EmployeeMvc.Configuration;

import com.example.EmployeeMvc.Filter.JwtAuthenticationFilter;
import com.example.EmployeeMvc.Security.JwtUtils;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

@Configuration
public class projectSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtils jwtUtils) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/customLogin", "/", "/access-denied", "/perform_login", "/css/**", "/js/**", "/images/**").permitAll()
//                        // admin-only areas
//                        .requestMatchers(HttpMethod.GET, "/employees").hasAnyRole("ADMIN", "MANAGER", "HR", "USER", "GUEST")
//                        .requestMatchers(HttpMethod.POST, "/employee/**").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/employee/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/employee/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
        .authorizeHttpRequests(request -> request
                .anyRequest().permitAll()
        )

                .formLogin(form -> form
                        .loginPage("/customLogin")
                        .loginProcessingUrl("/perform_login")
                        .successHandler(authenticationSuccessHandler(jwtUtils)) // send token instead of redirect
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                // make JWT the only source of authentication → no sessions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // add JWT filter for every request after login
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtUtils jwtUtils) {
        return (request, response, authentication) -> {
            // collect all roles
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            // generate JWT
            String token = jwtUtils.generateTokens(authentication.getName(), roles);

            // put JWT in cookie
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/"); // available for whole app
            jwtCookie.setMaxAge(24 * 60 * 60); // optional: 1 day
            response.addCookie(jwtCookie);

            // redirect to home page
            response.sendRedirect("/employees");
        };
    }

}

