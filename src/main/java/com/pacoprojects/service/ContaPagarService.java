package com.pacoprojects.service;

import com.pacoprojects.dto.ContaPagarDto;
import com.pacoprojects.dto.ResponseContaPagarDto;
import com.pacoprojects.dto.projections.ContaPagarProjections;
import com.pacoprojects.mapper.ContaPagarMapper;
import com.pacoprojects.mapper.ContaPagarRepository;
import com.pacoprojects.model.ContaPagar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaPagarService {

    private final ContaPagarRepository repositoryContaPagar;
    private final ContaPagarMapper mapperContaPagar;


    public List<ContaPagarProjections> getAllContaPagarByDescricao(String descricao) {
        return repositoryContaPagar.findAllByDescricaoContainsIgnoreCase(descricao);
    }

    public List<ContaPagarProjections> getAllContaPagar(Long idEmpresa) {
        return repositoryContaPagar.findAllByEmpresa_Id(idEmpresa);
    }

    public ResponseContaPagarDto getContaPagarById(Long id) {
        return mapperContaPagar.toDto1(repositoryContaPagar.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma conta a pagar.")));
    }

    public ContaPagarDto addContaPagar(ContaPagarDto contaPagarDto) {

        validateContaPagar(contaPagarDto);

        ContaPagar entity = repositoryContaPagar.save(mapperContaPagar.toEntity(contaPagarDto));
        return mapperContaPagar.toDto(entity);
    }


    public void deleteContaPagar(Long id) {

        if (repositoryContaPagar.existsById(id)) {
            repositoryContaPagar.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Essa conta a pagar não existe.");
        }
    }


    private void validateContaPagar(ContaPagarDto contaPagarDto) {

        if (contaPagarDto.empresa() == null || contaPagarDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        }

        if (contaPagarDto.pessoa() == null || contaPagarDto.pessoa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa deve ser informado.");
        }

        if (contaPagarDto.pessoaFornecedor() == null || contaPagarDto.pessoaFornecedor().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fornecedor responsável deve ser informado.");
        }

        if (contaPagarDto.dataVencimento().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de vencimento deve ser a partir do dia atual.");
        }

        if (repositoryContaPagar.existsByDescricaoContainsIgnoreCaseAndPessoa_Id(contaPagarDto.descricao().trim(), contaPagarDto.pessoa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe conta pagar, com essa descrição.");
        }
    }
}
