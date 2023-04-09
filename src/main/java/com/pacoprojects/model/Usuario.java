package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table( name = "usuario",
        uniqueConstraints = @UniqueConstraint(name = "unique_username", columnNames = "username"))
@Entity
public class Usuario implements UserDetails {

    @Id
    @SequenceGenerator(name = "sequence_usuario", sequenceName = "sequence_usuario", allocationSize = 1)
    @GeneratedValue(generator = "sequence_usuario", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Login obrigatório.")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Senha obrigatório.")
    @Column(name = "password", nullable = false)
    private String password;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date_last_password_change", nullable = false)
    private LocalDateTime dateLastPasswordChange;

    @Column(name = "jwt", columnDefinition = "TEXT")
    private String jwt;

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            inverseForeignKey = @ForeignKey(name = "role_id_fk", value = ConstraintMode.CONSTRAINT),
            uniqueConstraints = @UniqueConstraint(name = "unique_usuario_role", columnNames = {"usuario_id", "role_id"}))
    private Set<Role> authorities = new LinkedHashSet<>();

    @Column(name = "enabled")
    private boolean enabled = true;

    @OneToOne(targetEntity = Pessoa.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa pessoa;

    @ManyToOne(targetEntity = Pessoa.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa empresa;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Usuario usuario = (Usuario) o;
        return getId() != null && Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
