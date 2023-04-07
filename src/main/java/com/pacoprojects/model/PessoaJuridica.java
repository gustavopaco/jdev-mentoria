package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "pessoa_juridica")
@Entity
@PrimaryKeyJoinColumn(name = "pessoa_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "pessoa_juridica_fk", value = ConstraintMode.CONSTRAINT))
public class PessoaJuridica extends Pessoa {

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @NotBlank(message = "Inscrição Estadual obrigatório.")
    @Column(nullable = false)
    private String inscricaoEstadual;

    @NotBlank(message = "Inscrição Municipal obrigatório.")
    private String inscricaoMunicipal;

    @NotBlank(message = "Nome Fantasia obrigatório.")
    @Column(nullable = false)
    private String nomeFantasia;

    @NotBlank(message = "Razão Social obrigatório.")
    @Column(nullable = false)
    private String razaoSocial;

    private String categoria;
}
