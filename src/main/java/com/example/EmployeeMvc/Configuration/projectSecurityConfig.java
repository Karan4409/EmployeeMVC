package com.example.EmployeeMvc.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class projectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/customLogin")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/employees", true)
                        .permitAll()
                )
                .httpBasic(withDefaults());

        return http.build();

//    @Bean
//        public PasswordEncoder passwordEncoder(){
//            return passwordEncoder.DelegationPasswordEncoder();
//    }
    };
}
