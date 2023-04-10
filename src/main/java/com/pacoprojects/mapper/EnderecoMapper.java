package com.pacoprojects.mapper;

import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.model.Endereco;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnderecoMapper {

    EnderecoDto toDto(Endereco endereco);

    Endereco toEntity(EnderecoDto enderecoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(EnderecoDto enderecoDto, @MappingTarget Endereco endereco);
}
