package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pacoprojects.enums.StatusContaReceber;
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
@Table(name = "conta_receber")
@Entity
public class ContaReceber {

    @Id
    @SequenceGenerator(name = "sequence_conta_receber", sequenceName = "sequence_conta_receber", allocationSize = 1)
    @GeneratedValue(generator = "sequence_conta_receber", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Descrição obrigatório.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Status da conta obrigatório.")
    @Column(name = "status", nullable = false)
    private StatusContaReceber status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

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
        ContaReceber that = (ContaReceber) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
