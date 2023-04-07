package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "pessoa_fisica")
@Entity
@PrimaryKeyJoinColumn(name = "pessoa_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "pessoa_fisica_fk", value = ConstraintMode.CONSTRAINT))
public class PessoaFisica extends Pessoa{

    @CPF(message = "CPF obrigatório.")
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

}
