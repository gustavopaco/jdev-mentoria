package com.pacoprojects.service;

import com.pacoprojects.dto.FormaPagamentoDto;
import com.pacoprojects.dto.projections.FormaPagamentoProjection;
import com.pacoprojects.mapper.FormaPagamentoMapper;
import com.pacoprojects.model.FormaPagamento;
import com.pacoprojects.repository.FormaPagamentoRepository;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repositoryFormaPagamento;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final FormaPagamentoMapper mapperFormaPagamento;

    public List<FormaPagamentoProjection> getAllFormaPagamento(Long idEmpresa) {
        return null;
    }

    public FormaPagamentoProjection getFormaPagamentoById(Long id) {
        return repositoryFormaPagamento.findFormaPagamentoById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento não foi encontrado."));
    }

    public List<FormaPagamentoProjection> getAllFormaPagamentoByName(String name, Long idEmpresa) {
        return null;
    }

    public FormaPagamentoDto addFormaPagamento(FormaPagamentoDto formaPagamentoDto) {

        validateFormaPagamento(formaPagamentoDto);

        FormaPagamento entity = repositoryFormaPagamento.save(mapperFormaPagamento.toEntity(formaPagamentoDto));

        return  mapperFormaPagamento.toDto(entity);
    }

    public void deleteFormaPagamento(Long id) {

        if (repositoryFormaPagamento.existsById(id)) {
            repositoryFormaPagamento.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento não foi encontrado.");
        }

    }

    private void validateFormaPagamento(FormaPagamentoDto formaPagamentoDto) {

        if (formaPagamentoDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento obrigatório");
        }

        if (formaPagamentoDto.empresa() == null || formaPagamentoDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        } else if (!repositoryJuridica.existsById(formaPagamentoDto.empresa().id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        }

        if (repositoryFormaPagamento.existsByDescricaoAndEmpresa_Id(formaPagamentoDto.descricao(), formaPagamentoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma forma de pagamento com esse nome.");
        }

    }
}
