package com.pacoprojects.dto.projections;

import com.pacoprojects.model.PessoaJuridica;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;



/**
 * A Projection for the {@link com.pacoprojects.model.PessoaJuridica} entity
 */
@Projection(name = "pessoaJuridicaProjection", types = PessoaJuridica.class)
public interface PessoaJuridicaProjection {
    Long getId();

    String getNome();

    String getEmail();

    String getCnpj();

    String getInscricaoEstadual();

    String getInscricaoMunicipal();

    String getNomeFantasia();

    String getRazaoSocial();

    String getCategoria();

    Set<TelefoneProjection> getTelefones();

    Set<EnderecoProjection> getEnderecos();

    Set<ContaReceberProjection> getContasReceber();
}
