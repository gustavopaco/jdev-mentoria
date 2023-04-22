package com.pacoprojects.security;

import com.pacoprojects.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ApplicationConfig applicationConfig;
    private final JwtAuthorizationFilter authorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/error", "/health", "/auth/**", "/actuator/**").permitAll()
                .requestMatchers("/mvc/**", "/resources/**", "/static/**", "/templates/**", "classpath:/resources/**" ,"classpath:/static/**", "classpath:/templates/**").permitAll()
                .requestMatchers(
                        "/roles/**",
                        "/pessoas/**",
                        "/categorias/**",
                        "/produtos/**",
                        "/marcasProdutos/**",
                        "/contasPagar/**",
                        "/notaFiscalCompra/**",
                        "/itemNotaProduto/**",
                        "/imagensProduto/**",
                        "/avaliacaoProdutos/**",
                        "/formaPagamento/**",
                        "/vendaCompra/**",
                        "/statusRastreio/**",
                        "/cupomDesconto/**",
                        "/report/**",
                        "/pagamento/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(applicationConfig.authenticationProvider());

        return http.build();
    }
}
