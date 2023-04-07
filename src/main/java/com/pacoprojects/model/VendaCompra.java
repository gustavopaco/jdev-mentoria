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
@Table(name = "venda_compra")
@Entity
public class VendaCompra {

    @Id
    @SequenceGenerator(name = "sequence_venda_compra", sequenceName = "sequence_venda_compra", allocationSize = 1)
    @GeneratedValue(generator = "sequence_venda_compra", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

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

    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;


    private BigDecimal valorFrete;

    private Integer diaEntrega;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate dataVenda;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;

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
            nullable = false,
            foreignKey = @ForeignKey(name = "cupom_desconto_id_fk", value = ConstraintMode.CONSTRAINT))
    private CupomDesconto cupomDesconto;

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
