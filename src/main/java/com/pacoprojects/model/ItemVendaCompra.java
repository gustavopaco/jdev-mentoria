package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "item_venda_compra")
@Entity
public class ItemVendaCompra {

    @Id
    @SequenceGenerator(name = "sequence_item_venda_compra", sequenceName = "sequence_item_venda_compra", allocationSize = 1)
    @GeneratedValue(generator = "sequence_item_venda_compra", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = "Quantidade de itens obrigatório.")
    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(
            name = "produto_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "produto_id_fk", value = ConstraintMode.CONSTRAINT))
    private Produto produto;

    @ManyToOne(targetEntity = VendaCompra.class)
    @JoinColumn(
            name = "venda_compra_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_compra_id_fk", value = ConstraintMode.CONSTRAINT))
    private VendaCompra vendaCompra;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemVendaCompra that = (ItemVendaCompra) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
