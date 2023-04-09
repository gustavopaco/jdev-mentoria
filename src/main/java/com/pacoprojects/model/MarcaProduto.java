package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "marca_produto")
@Entity
public class MarcaProduto {

    @Id
    @SequenceGenerator(name = "sequence_marca_produto", sequenceName = "sequence_marca_produto", allocationSize = 1)
    @GeneratedValue(generator = "sequence_marca_produto", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Nome da marca é obrigatório.")
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MarcaProduto that = (MarcaProduto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
