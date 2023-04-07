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
@Table(name = "cupom_desconto")
@Entity
public class CupomDesconto {

    @Id
    @SequenceGenerator(name = "sequence_cupom_desconto", sequenceName = "sequence_cupom_desconto", allocationSize = 1)
    @GeneratedValue(generator = "sequence_cupom_desconto", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    private String codigoDescricao;

    private BigDecimal valorRealDesconto;

    private BigDecimal valorPorcentagemDescricao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate validadeCupom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CupomDesconto that = (CupomDesconto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
