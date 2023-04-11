package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pacoprojects.enums.StatusContaPagar;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conta_pagar")
@Entity
public class ContaPagar {

    @Id
    @SequenceGenerator(name = "sequence_conta_pagar", sequenceName = "sequence_conta_pagar", allocationSize = 1)
    @GeneratedValue(generator = "sequence_conta_pagar", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Descrição da conta a pagar obrigatório.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Status da conta obrigatório.")
    @Column(name = "status", nullable = false)
    private StatusContaPagar status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @Future(message = "Data de vencimento deve ser a partir do dia atual.")
    @NotNull(message = "Data de vencimento obrigatório.")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull(message = "Valor total obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "valor_desconto")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal valorDesconto;

    @NotNull(message = "Pessoa deve ser informada.")
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private PessoaJuridica pessoa;

    @NotNull(message = "Fornecedor responsável deve ser informado.")
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_fornecedor_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_fornecedor_id_fk", value = ConstraintMode.CONSTRAINT))
    private PessoaJuridica pessoaFornecedor;

    @NotNull(message = "Empresa deve ser informado.")
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private PessoaJuridica empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContaPagar that = (ContaPagar) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
