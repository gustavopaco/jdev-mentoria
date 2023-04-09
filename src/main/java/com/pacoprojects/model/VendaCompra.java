package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
@Table(name = "venda_compra")
@Entity
public class VendaCompra {

    @Id
    @SequenceGenerator(name = "sequence_venda_compra", sequenceName = "sequence_venda_compra", allocationSize = 1)
    @GeneratedValue(generator = "sequence_venda_compra", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = "Valor total obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_total",  nullable = false)
    private BigDecimal valorTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @NotNull(message = "Valor do frete obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_frete",  nullable = false)
    private BigDecimal valorFrete;

    @Column(name = "dia_entrega",  nullable = false)
    private Integer diasParaEntrega;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @Column(name = "data_venda", nullable = false)
    private LocalDate dataVenda;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa pessoa;

    @ManyToOne(targetEntity = Endereco.class)
    @JoinColumn(
            name = "endereco_entrega_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "endereco_entrega_id_fk", value = ConstraintMode.CONSTRAINT))
    private Endereco enderecoEntrega;

    @ManyToOne(targetEntity = Endereco.class)
    @JoinColumn(
            name = "endereco_cobranca_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "endereco_cobranca_id_fk", value = ConstraintMode.CONSTRAINT))
    private Endereco enderecoCobranca;

    @ManyToOne(targetEntity = FormaPagamento.class)
    @JoinColumn(
            name = "forma_pagamento_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "forma_pagamento_id_fk", value = ConstraintMode.CONSTRAINT))
    private FormaPagamento formaPagamento;

    @OneToOne(targetEntity = NotaFiscalVenda.class)
    @JoinColumn(
            name = "nota_fiscal_venda_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "nota_fiscal_venda_id_fk", value = ConstraintMode.CONSTRAINT))
    private NotaFiscalVenda notaFiscalVenda;

    @ManyToOne(targetEntity = CupomDesconto.class)
    @JoinColumn(
            name = "cupom_desconto_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "cupom_desconto_id_fk", value = ConstraintMode.CONSTRAINT))
    private CupomDesconto cupomDesconto;

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
        VendaCompra that = (VendaCompra) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
