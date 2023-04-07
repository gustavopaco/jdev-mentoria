package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
