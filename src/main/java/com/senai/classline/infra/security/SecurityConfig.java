package com.senai.classline.infra.security;

import com.senai.classline.infra.security.JwtSecurityFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtSecurityFilter jwtSecurityFilter;

    // 🔐 Configuração para Instituições
    @Bean
    @Order(1)
    public SecurityFilterChain instituicaoSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/instituicao/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/instituicao/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    // 🔐 Configuração para Professores
    @Bean
    @Order(2)
    public SecurityFilterChain professorSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/professor/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/professor/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    @Order(3)
    public SecurityFilterChain cursoSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/curso/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain turmaSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/turma/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(5)
    public SecurityFilterChain alunoSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/aluno/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/aluno/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(6)
    public SecurityFilterChain semestreSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/semestre/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(7)
    public SecurityFilterChain discilpinaSecurity(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ou cors(Customizer.withDefaults())
                .securityMatcher("/discplina/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔑 Encoder padrão
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
