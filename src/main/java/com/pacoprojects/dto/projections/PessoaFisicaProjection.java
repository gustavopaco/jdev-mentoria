package com.pacoprojects.dto.projections;

import com.pacoprojects.model.PessoaFisica;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.Set;

/**
 * A Projection for the {@link com.pacoprojects.model.PessoaFisica} entity
 */
@Projection(name = "pessoaFisicaProjection", types = PessoaFisica.class)
public interface PessoaFisicaProjection {
    Long getId();

    String getNome();

    String getEmail();

    String getCpf();

    LocalDate getDataNascimento();

    Set<TelefoneProjection> getTelefones();

    Set<EnderecoProjection> getEnderecos();

    Set<ContaReceberProjection> getContasReceber();

    PessoaJuridicaProjection getEmpresa();
}
