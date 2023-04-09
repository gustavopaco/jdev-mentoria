package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Número da nota obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotBlank(message = "Série da nota obrigatório.")
    @Column(name = "serie", nullable = false)
    private String serie;

    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Valor total obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @NotNull(message = "Valor de ICMS obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_icms", nullable = false)
    private BigDecimal valorIcms;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @NotNull(message = "Data da compra obrigatório.")
    @Column(name = "data_compra", nullable = false)
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
        NotaFiscalCompra that = (NotaFiscalCompra) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
