package com.pacoprojects.service;

import com.pacoprojects.dto.PessoaFisicaDto;
import com.pacoprojects.dto.VendaCompraDto;
import com.pacoprojects.dto.projections.VendaCompraProjection;
import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.mapper.PessoaFisicaMapper;
import com.pacoprojects.mapper.VendaCompraMapper;
import com.pacoprojects.model.PessoaFisica;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaCompraService {

    private final VendaCompraRepository repositoryVendaCompra;
    private final PessoaFisicaRepository repositoryFisica;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final EnderecoRepository repositoryEndereco;
    private final FormaPagamentoRepository repositoryFormaPagamento;
    private final VendaCompraMapper mapperVendaCompra;
    private final PessoaUserService servicePessoaUser;
    private final PessoaFisicaMapper mapperFisica;
    private final NotaFiscalVendaRepository repositoryNotaFiscalVenda;


    public VendaCompraDto addVendaCompra(VendaCompraDto vendaCompraDto) {

        validaVendaCompra(vendaCompraDto);

        // TODO Quando for cadastrar um nova Venda e usuario novo, enviar o Codigo da empresa dentro do objeto PessoaFisicaDto alem de dentro da VendaCompraDto
        VendaCompra entity = null;
        if (vendaCompraDto.pessoa().id() == null) {
            entity = registerNewUserBeforePurchase(vendaCompraDto);
        } else {
            entity = mapperVendaCompra.toEntity(vendaCompraDto);
        }

            entity.getNotaFiscalVenda().setEmpresa(entity.getEmpresa());
            entity.getNotaFiscalVenda().setVendaCompra(entity);
            entity = repositoryVendaCompra.save(entity);

        return mapperVendaCompra.toDto(entity);
    }

    private VendaCompra registerNewUserBeforePurchase(VendaCompraDto vendaCompraDto) {

        PessoaFisicaDto pessoaFisicaDto = servicePessoaUser.addPessoaFisica(vendaCompraDto.pessoa());

        PessoaFisica fisicaEntity = mapperFisica.toEntity(pessoaFisicaDto);
        VendaCompra entity = mapperVendaCompra.toEntity(vendaCompraDto);

        entity.setPessoa(fisicaEntity);
        entity.setEnderecoCobranca(fisicaEntity.getEnderecos().stream()
                .filter(endereco ->
                        endereco.getTipoEndereco().equals(TipoEndereco.COBRANCA))
                .findFirst().orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao gerar venda, mas cliente foi registrado com sucesso, tente novamente")));
        entity.setEnderecoEntrega(fisicaEntity.getEnderecos().stream()
                .filter(endereco ->
                        endereco.getTipoEndereco().equals(TipoEndereco.ENTREGA))
                .findFirst().orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao gerar venda, mas cliente foi registrado com sucesso, tente novamente")));
        return entity;
    }

    public void deleteVendaCompra(Long id) {

        if (repositoryVendaCompra.existsById(id)) {
            repositoryVendaCompra.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma venda com esse código");
        }
    }

    private void validaVendaCompra(VendaCompraDto vendaCompraDto) {

        if (vendaCompraDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Venda não pode ser nula.");
        }

        if (vendaCompraDto.pessoa() != null && vendaCompraDto.pessoa().id() != null) {
            if (!repositoryFisica.existsById(vendaCompraDto.pessoa().id())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa física deve ser informada.");
            } else if (vendaCompraDto.enderecoCobranca() == null || vendaCompraDto.enderecoCobranca().id() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço de cobrança deve ser informado.");
            } else if (!repositoryEndereco.existsById(vendaCompraDto.enderecoCobranca().id())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço de cobrança deve ser informado.");
            }
        }

        if (vendaCompraDto.empresa() == null || vendaCompraDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        } else if (!repositoryJuridica.existsById(vendaCompraDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        }

        if (vendaCompraDto.formaPagamento() == null || vendaCompraDto.formaPagamento().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento deve ser informado.");
        } else if (!repositoryFormaPagamento.existsById(vendaCompraDto.formaPagamento().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento deve ser informado.");
        }

    }

    public List<VendaCompraProjection> getAllVendaCompra(Long idEmpresa) {
        return null;
    }

    public VendaCompraProjection getVendaCompraById(Long id) {
        return null;
    }

    public List<VendaCompraProjection> getAllVendaCompraByName(String name, Long idEmpresa) {
        return null;
    }
}
