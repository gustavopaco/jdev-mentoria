package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "acesso_end_point", uniqueConstraints = @UniqueConstraint(name = "unique_nome", columnNames = "nome"))
@Entity
public class AcessoEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_acesso_end_point")
    @SequenceGenerator(name = "sequence_acesso_end_point", sequenceName = "sequence_acesso_end_point", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AcessoEndpoint that = (AcessoEndpoint) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
