package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "role")
@Entity
public class Role implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "sequence_role", sequenceName = "sequence_role", allocationSize = 1)
    @GeneratedValue(generator = "sequence_role", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Role obrigatório.")
    @Column(name = "authority", nullable = false)
    private String authority;

    @ManyToMany(targetEntity = Usuario.class, mappedBy = "authorities")
    @ToString.Exclude
    public Set<Usuario> usuarios = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
