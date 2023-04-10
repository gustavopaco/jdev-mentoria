package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @SequenceGenerator(name = "sequence_pessoa", sequenceName = "sequence_pessoa", allocationSize = 1)
    @GeneratedValue(generator = "sequence_pessoa", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Nome obrigatório.")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Email(message = "Formato do E-mail inválido.")
    @NotBlank(message = "E-mail obrigatório.")
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(targetEntity = Telefone.class, mappedBy = "pessoa", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @ToString.Exclude
    private Set<Telefone> telefones = new LinkedHashSet<>();

    @OneToMany(targetEntity = Endereco.class, mappedBy = "pessoa", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @ToString.Exclude
    private Set<Endereco> enderecos = new LinkedHashSet<>();

    @OneToMany(targetEntity = ContaReceber.class, mappedBy = "pessoa", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @ToString.Exclude
    private Set<ContaReceber> contasReceber = new LinkedHashSet<>();

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "empresa_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pessoa pessoa = (Pessoa) o;
        return getId() != null && Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
