package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "telefone")
@Entity
public class Telefone {

    @Id
    @SequenceGenerator(name = "sequence_telefone", sequenceName = "sequence_telefone", allocationSize = 1)
    @GeneratedValue(generator = "sequence_telefone", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Número obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @JsonIgnore //Ignorar Propriedade no Json
    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa pessoa;

    @JsonIgnore //Ignorar Propriedade no Json
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
        Telefone telefone = (Telefone) o;
        return getId() != null && Objects.equals(getId(), telefone.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
