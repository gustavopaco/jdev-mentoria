package com.pacoprojects.model;

import jakarta.persistence.*;
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
@Table(name = "avaliacao_produto")
@Entity
public class AvaliacaoProduto {

    @Id
    @SequenceGenerator(name = "sequence_avaliacao_produto", sequenceName = "sequence_avaliacao_produto", allocationSize = 1)
    @GeneratedValue(generator = "sequence_avaliacao_produto", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    private String descricao;

    private Integer nota;

    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(
            name = "produto_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "produto_id_fk", value = ConstraintMode.CONSTRAINT))
    private Produto produto;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa pessoa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AvaliacaoProduto that = (AvaliacaoProduto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
