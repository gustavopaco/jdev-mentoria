package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Column(name = "token_autenticacao")
    private String token_autenticacao;

    @Column(name = "pix_key")
    private String pixKey;

    public boolean isTokenExpired() {
        Duration duration = Duration.between(this.dataCadastro, LocalDateTime.now());
        return duration.compareTo(Duration.ofHours(23)) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccessTokenJuno that = (AccessTokenJuno) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
