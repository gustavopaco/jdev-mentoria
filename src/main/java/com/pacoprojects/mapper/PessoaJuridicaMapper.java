package com.pacoprojects.mapper;

import com.pacoprojects.dto.PessoaJuridicaDto;
import com.pacoprojects.model.PessoaJuridica;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PessoaJuridicaMapper {

    PessoaJuridica toEntity(PessoaJuridicaDto pessoaJuridicaDto);

    PessoaJuridicaDto toDto(PessoaJuridica pessoaJuridica);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PessoaJuridica partialUpdate(PessoaJuridicaDto pessoaJuridicaDto, @MappingTarget PessoaJuridica pessoaJuridica);

    @AfterMapping
    default void linkTelefones(@MappingTarget PessoaJuridica pessoaJuridica) {
        pessoaJuridica.getTelefones().forEach(telefone -> {
            telefone.setPessoa(pessoaJuridica);
            telefone.setEmpresa(pessoaJuridica);
        });
    }

    @AfterMapping
    default void linkEnderecos(@MappingTarget PessoaJuridica pessoaJuridica) {
        pessoaJuridica.getEnderecos().forEach(endereco -> {
            endereco.setPessoa(pessoaJuridica);
            endereco.setEmpresa(pessoaJuridica);
        });
    }

    @AfterMapping
    default void linkContasReceber(@MappingTarget PessoaJuridica pessoaJuridica) {
        pessoaJuridica.getContasReceber().forEach(contasReceber -> {
            contasReceber.setPessoa(pessoaJuridica);
            contasReceber.setEmpresa(pessoaJuridica);
        });
    }
}
