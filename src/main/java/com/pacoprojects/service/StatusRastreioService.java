package com.pacoprojects.service;

import com.pacoprojects.api.integration.melhor.envio.response.rastreio.pedido.ResponseMelhorEnvioRastreioPedidoDto;
import com.pacoprojects.dto.projections.StatusRastreioProjection;
import com.pacoprojects.mapper.StatusRastreioMapper;
import com.pacoprojects.model.StatusRastreio;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.StatusRastreioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusRastreioService {

    private final StatusRastreioRepository repositoryStatusRastreio;
    private final StatusRastreioMapper mapperStatusRastreio;

    public void saveNewStatusRastreio(VendaCompra vendaCompra, String urlRastreio) {

        Optional<StatusRastreio> optionalStatusRastreio = repositoryStatusRastreio.findByVendaCompra_Id(vendaCompra.getId());

        StatusRastreio statusRastreio;
        if (optionalStatusRastreio.isEmpty()) {
            statusRastreio = new StatusRastreio();
            statusRastreio.setUrlRastreio(urlRastreio);
            statusRastreio.setEmpresa(vendaCompra.getEmpresa());
            statusRastreio.setVendaCompra(vendaCompra);
        } else {
            statusRastreio = optionalStatusRastreio.get();
            statusRastreio.setUrlRastreio(urlRastreio);
        }
        repositoryStatusRastreio.save(statusRastreio);
    }

    public ResponseMelhorEnvioRastreioPedidoDto rastrearVenda(Long idVenda) {
        return mapperStatusRastreio.toDto(repositoryStatusRastreio.findByVendaCompra_Id(idVenda)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe rastreio para essa venda ainda.")));
    }

    public List<StatusRastreioProjection> getAllStatusRastreioByIdVenda(Long idVenda) {
        return repositoryStatusRastreio.findAllByVendaCompra_IdAndVendaCompra_Enabled(idVenda, true, Sort.by("id"));
    }
}
