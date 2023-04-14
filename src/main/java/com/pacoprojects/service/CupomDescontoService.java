package com.pacoprojects.service;

import com.pacoprojects.dto.CupomDescontoDto;
import com.pacoprojects.mapper.CupomDescontoMapper;
import com.pacoprojects.model.CupomDesconto;
import com.pacoprojects.repository.CupomDescontoRepository;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CupomDescontoService {

    private final CupomDescontoRepository repositoryCupomDesconto;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final CupomDescontoMapper mapperCupomDesconto;

    public List<CupomDescontoDto> getAllCupomDescontoByEmpresaId(Long idEmpresa) {
        return repositoryCupomDesconto.findAllByEmpresa_Id(idEmpresa)
                .stream().map(mapperCupomDesconto::toDto).collect(Collectors.toList());
    }

    public CupomDescontoDto getCupomDescontoById(Long idCupom) {
            return repositoryCupomDesconto.findById(idCupom)
                    .map(mapperCupomDesconto::toDto).orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupom não foi encontrado."));
    }

    public CupomDescontoDto getCupomDescontoByDescricao(String codigoDescricao, Long idEmpresa) {
        return mapperCupomDesconto.toDto(repositoryCupomDesconto
                .findByCodigoDescricaoAndEmpresa_Id(codigoDescricao, idEmpresa)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupom não foi encontrado.")));
    }

    public CupomDescontoDto addCupomDesconto(CupomDescontoDto cupomDescontoDto) {

        validateCupomDesconto(cupomDescontoDto);

        CupomDesconto entity = repositoryCupomDesconto.save(mapperCupomDesconto.toEntity(cupomDescontoDto));

        return mapperCupomDesconto.toDto(entity);
    }

    public void deleteCupomDesconto(Long id) {
        if (repositoryCupomDesconto.existsById(id)) {
            // TODO deletar pode Id, porem esta amarrado em Empresa teria que remover a referencia de chave estrangeira da Empresa e depois deletar o Cupom
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupom não foi encontrado no sistema");
        }
    }

    private void validateCupomDesconto(CupomDescontoDto cupomDescontoDto) {

        if (cupomDescontoDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupom de desconto deve ser informado.");
        }

        if (cupomDescontoDto.empresa() == null || cupomDescontoDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informado.");
        } else if (!repositoryJuridica.existsById(cupomDescontoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informado.");
        }

        if (cupomDescontoDto.id() == null && repositoryCupomDesconto.existsByCodigoDescricaoAndEmpresa_Id(
                        cupomDescontoDto.codigoDescricao(), cupomDescontoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um cupom com essa descrição");

        } else if (cupomDescontoDto.id() != null && repositoryCupomDesconto.existsByCodigoDescricaoAndEmpresa_IdAndIdNot(
                        cupomDescontoDto.codigoDescricao(), cupomDescontoDto.empresa().id(), cupomDescontoDto.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um cupom com essa descrição");
        }
    }
}
