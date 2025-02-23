package com.polylvst.simplepos.config;

import com.polylvst.simplepos.domain.Role;
import com.polylvst.simplepos.domain.entities.User;
import com.polylvst.simplepos.repositories.UserRepository;
import com.polylvst.simplepos.security.JwtAuthenticationFilter;
import com.polylvst.simplepos.security.MyUserDetailsService;
import com.polylvst.simplepos.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        String adminUsername = "admin";
        userRepository.findByUsername(adminUsername).orElseGet(() -> {
            User newUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            return userRepository.save(newUser);
        });
        return new MyUserDetailsService(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        String ROLE_ADMIN = "ADMIN";
        String ROLE_KASIR = "KASIR";
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole(ROLE_ADMIN)

                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAnyRole(ROLE_ADMIN, ROLE_KASIR)
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole(ROLE_ADMIN)
                        
                        .requestMatchers(HttpMethod.GET, "/api/v1/transactions/**").hasAnyRole(ROLE_ADMIN, ROLE_KASIR)
                        .requestMatchers(HttpMethod.POST, "/api/v1/transactions/**").hasAnyRole(ROLE_ADMIN, ROLE_KASIR)
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
