package com.pacoprojects.mapper;

import com.pacoprojects.dto.PessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.model.PessoaFisica;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PessoaFisicaMapper {
    PessoaFisica toEntity(RegisterPessoaFisicaDto registerPessoaFisicaDto);

    PessoaFisica toEntity(PessoaFisicaDto pessoaFisicaDto);

    RegisterPessoaFisicaDto toDto(PessoaFisica pessoaFisica);

    PessoaFisicaDto toDto1(PessoaFisica pessoaFisica);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PessoaFisica partialUpdate(RegisterPessoaFisicaDto registerPessoaFisicaDto, @MappingTarget PessoaFisica pessoaFisica);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PessoaFisica partialUpdate(PessoaFisicaDto pessoaFisicaDto, @MappingTarget PessoaFisica pessoaFisica);

    @AfterMapping
    default void linkTelefones(@MappingTarget PessoaFisica pessoaFisica) {
        pessoaFisica.getTelefones().forEach(telefone -> {
            telefone.setPessoa(pessoaFisica);
            telefone.setEmpresa(pessoaFisica.getEmpresa());
        });
    }

    @AfterMapping
    default void linkEnderecos(@MappingTarget PessoaFisica pessoaFisica) {
        pessoaFisica.getEnderecos().forEach(endereco -> {
            endereco.setPessoa(pessoaFisica);
            endereco.setEmpresa(pessoaFisica.getEmpresa());
        });
    }

    @AfterMapping
    default void linkContasReceber(@MappingTarget PessoaFisica pessoaFisica) {
        pessoaFisica.getContasReceber().forEach(contasReceber -> {
            contasReceber.setPessoa(pessoaFisica);
            contasReceber.setEmpresa(pessoaFisica.getEmpresa());
        });
    }
}
