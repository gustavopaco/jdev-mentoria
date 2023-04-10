package com.pacoprojects.mapper;

import com.pacoprojects.dto.PessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.model.PessoaFisica;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PessoaFisicaMapper {
    PessoaFisica toEntity(RegisterPessoaFisicaDto registerPessoaFisicaDto);

    RegisterPessoaFisicaDto toDto(PessoaFisica pessoaFisica);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PessoaFisica partialUpdate(RegisterPessoaFisicaDto registerPessoaFisicaDto, @MappingTarget PessoaFisica pessoaFisica);

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

    PessoaFisica toEntity1(PessoaFisicaDto pessoaFisicaDto);

    PessoaFisicaDto toDto1(PessoaFisica pessoaFisica);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PessoaFisica partialUpdate1(PessoaFisicaDto pessoaFisicaDto, @MappingTarget PessoaFisica pessoaFisica);


}
