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
@Table(name = "forma_pagamento")
@Entity
public class FormaPagamento {

    @Id
    @SequenceGenerator(name = "sequence_forma_pagamento", sequenceName = "sequence_forma_pagamento", allocationSize = 1)
    @GeneratedValue(generator = "sequence_forma_pagamento", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String descricao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FormaPagamento that = (FormaPagamento) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
