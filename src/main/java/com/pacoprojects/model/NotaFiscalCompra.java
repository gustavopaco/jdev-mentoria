package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "nota_fiscal_compra")
@Entity
public class NotaFiscalCompra {

    @Id
    @SequenceGenerator(name = "sequence_nota_fiscal_compra", sequenceName = "sequence_nota_fiscal_compra", allocationSize = 1)
    @GeneratedValue(generator = "sequence_nota_fiscal_compra", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    private String numero;

    private String serie;

    private String descricao;

    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    private BigDecimal valorICMS;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate dataCompra;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa pessoa;

    @OneToOne(targetEntity = ContaPagar.class)
    @JoinColumn(
            name = "conta_pagar_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "conta_pagar_id_fk", value = ConstraintMode.CONSTRAINT))
    private ContaPagar contaPagar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NotaFiscalCompra that = (NotaFiscalCompra) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
