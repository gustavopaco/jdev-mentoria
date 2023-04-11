package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoa_fisica")
@Entity
@PrimaryKeyJoinColumn(name = "pessoa_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "pessoa_fisica_fk", value = ConstraintMode.CONSTRAINT))
public class PessoaFisica extends Pessoa{

    @CPF(message = "CPF inválido.")
    @NotBlank(message = "CPF obrigatório.")
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

}
