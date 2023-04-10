package com.pacoprojects.security;

import com.pacoprojects.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository
                .findUsuarioByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não foi encontrado."));

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public WebMvcConfigurer addCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
               registry
                       .addMapping("/**")
                       .allowedHeaders("*")
                       .allowedMethods("*")
                       .allowedOrigins("*");
            }
        };
    }

    // O Alex implementou a interface AsyncConfigurer na assinatura da classe ApplicationConfig e depois fez @override nesse metodo getAsyncExecutor()
    @Bean
    public AsyncConfigurer configurerAsync() {
        return new AsyncConfigurer() {
            @Override
            public Executor getAsyncExecutor() {

                ThreadPoolTaskExecutor thread = new ThreadPoolTaskExecutor();
                thread.setCorePoolSize(10);
                thread.setMaxPoolSize(20);
                thread.setQueueCapacity(500);
                thread.setAllowCoreThreadTimeOut(false);
                thread.setThreadNamePrefix("Async Thread");
                thread.initialize();
                return thread;
            }
        };
    }

    @Bean
    public RestTemplate getRestTemplateInstance() {
        return new RestTemplate();
    }
}
