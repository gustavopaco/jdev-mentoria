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
@Table(name = "nota_fiscal_venda")
@Entity
public class NotaFiscalVenda {

    @Id
    @SequenceGenerator(name = "sequence_nota_fiscal_venda", sequenceName = "sequence_nota_fiscal_venda", allocationSize = 1)
    @GeneratedValue(generator = "sequence_nota_fiscal_venda", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Número da nota obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotBlank(message = "Série da nota obrigatório.")
    @Column(name = "serie", nullable = false)
    private String serie;

    @NotBlank(message = "Tipo da nota obrigatório.")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotBlank(message = "Xml da nota obrigatório.")
    @Column(name = "xml", columnDefinition = "TEXT", nullable = false)
    private String xml;

    @NotBlank(message = "Pdf da nota obrigatório.")
    @Column(name = "pdf", columnDefinition = "TEXT", nullable = false)
    private String pdf;

    @OneToOne(targetEntity = VendaCompra.class)
    @JoinColumn(
            name = "venda_compra_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_compra_id_fk", value = ConstraintMode.CONSTRAINT))
    private VendaCompra vendaCompra;

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
        NotaFiscalVenda that = (NotaFiscalVenda) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
