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
@Table(name = "item_nota_produto")
@Entity
public class ItemNotaProduto {

    @Id
    @SequenceGenerator(name = "sequence_item_nota_produto", sequenceName = "sequence_item_nota_produto", allocationSize = 1)
    @GeneratedValue(generator = "sequence_item_nota_produto", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(
            name = "produto_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "produto_id_fk", value = ConstraintMode.CONSTRAINT))
    private Produto produto;

    @ManyToOne(targetEntity = NotaFiscalCompra.class)
    @JoinColumn(
            name = "nota_fiscal_compra_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "nota_fiscal_compra_id_fk", value = ConstraintMode.CONSTRAINT))
    private NotaFiscalCompra notaFiscalCompra;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemNotaProduto that = (ItemNotaProduto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
