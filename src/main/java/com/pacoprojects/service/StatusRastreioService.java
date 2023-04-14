package com.pacoprojects.service;

import com.pacoprojects.dto.projections.StatusRastreioProjection;
import com.pacoprojects.model.StatusRastreio;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.StatusRastreioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusRastreioService {

    private final StatusRastreioRepository repositoryStatusRastreio;

    public void addStatusRastreioAfterVendaCompraDone(VendaCompra vendaCompra) {

        StatusRastreio statusRastreio = new StatusRastreio();

        statusRastreio.setCentroDistribuicao("Loja Local");
        statusRastreio.setCidade("Belo Horizonte");
        statusRastreio.setEstado("MG");
        statusRastreio.setStatus("INICIO COMPRA");
        statusRastreio.setEmpresa(vendaCompra.getEmpresa());
        statusRastreio.setVendaCompra(vendaCompra);

        repositoryStatusRastreio.save(statusRastreio);
    }

    public List<StatusRastreioProjection> getAllStatusRastreioByIdVenda(Long idVenda) {
        return repositoryStatusRastreio.findAllByVendaCompra_IdAndVendaCompra_Enabled(idVenda, true, Sort.by("id"));
    }
}
