package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "categoria")
@Entity()
public class Categoria {

    @Id
    @SequenceGenerator(name = "sequence_categoria", sequenceName = "sequence_categoria", allocationSize = 1)
    @GeneratedValue(generator = "sequence_categoria", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Nome da Categoria obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToMany(targetEntity = Produto.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "categoria_produto",
    joinColumns = @JoinColumn(name = "categoria_id"),
    foreignKey = @ForeignKey(name = "categoria_id_fk", value = ConstraintMode.CONSTRAINT),
    inverseJoinColumns = @JoinColumn(name = "produto_id"),
    inverseForeignKey = @ForeignKey(name = "produto_id_fk", value = ConstraintMode.CONSTRAINT))
    @ToString.Exclude
    private Set<Produto> produtos = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Categoria categoria = (Categoria) o;
        return getId() != null && Objects.equals(getId(), categoria.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
