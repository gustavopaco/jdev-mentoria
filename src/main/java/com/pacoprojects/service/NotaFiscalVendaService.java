package com.pacoprojects.service;

import com.pacoprojects.dto.projections.NotaFiscalVendaProjection;
import com.pacoprojects.repository.NotaFiscalVendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class NotaFiscalVendaService {

    private final NotaFiscalVendaRepository repositoryNotaFiscalVenda;


    public NotaFiscalVendaProjection getNotaFiscalByIdVenda(Long idVenda) {
        return repositoryNotaFiscalVenda.findByVendaCompra_IdAndVendaCompra_Enabled(idVenda, true)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma nota fiscal com esse código de venda"));
    }
}
