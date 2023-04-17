package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Table(name = "access_token_juno_api")
@Entity
public class AccessTokenJuno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_access_token_juno_api")
    @SequenceGenerator(name = "sequence_access_token_juno_api", sequenceName = "sequence_access_token_juno_api", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "access_token", columnDefinition = "TEXT")
    private String access_token;

    @Column(name = "token_type")
    private String token_type;

    @Column(name = "expires_in")
    private Integer expires_in;

    @Column(name = "scope")
    private String scope;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "jti")
    private String jti;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Column(name = "token_autenticacao")
    private String token_autenticacao;

    public boolean isTokenExpired() {
        Duration duration = Duration.between(this.dataCadastro, LocalDateTime.now());

        return duration.compareTo(Duration.ofMinutes(50)) >= 0;
    }

}
