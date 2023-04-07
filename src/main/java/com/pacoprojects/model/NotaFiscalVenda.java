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
@Table(name = "nota_fiscal_venda")
@Entity
public class NotaFiscalVenda {

    @Id
    @SequenceGenerator(name = "sequence_nota_fiscal_venda", sequenceName = "sequence_nota_fiscal_venda", allocationSize = 1)
    @GeneratedValue(generator = "sequence_nota_fiscal_venda", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    private String numero;

    private String serie;

    private String tipo;

    @Column(name = "xml", columnDefinition = "TEXT")
    private String xml;

    @Column(name = "pdf", columnDefinition = "TEXT")
    private String pdf;

    @OneToOne(targetEntity = VendaCompra.class)
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
        NotaFiscalVenda that = (NotaFiscalVenda) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
