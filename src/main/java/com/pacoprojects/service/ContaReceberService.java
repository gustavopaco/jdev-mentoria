package com.pacoprojects.service;

import com.pacoprojects.enums.StatusContaReceber;
import com.pacoprojects.model.ContaReceber;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.ContaReceberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContaReceberService {

    private final ContaReceberRepository repositoryContaReceber;

    public void addContaReceberAfterVendaCompraDone(VendaCompra vendaCompra) {

        ContaReceber contaReceber = new ContaReceber();
        contaReceber.setDescricao("Venda da loja virtual número: " + vendaCompra.getId());
        contaReceber.setDataPagamento(LocalDate.now());
        contaReceber.setDataVencimento(LocalDate.now());
        contaReceber.setEmpresa(vendaCompra.getEmpresa());
        contaReceber.setPessoa(vendaCompra.getPessoa());
        contaReceber.setStatus(StatusContaReceber.QUITADA);
        contaReceber.setValorDesconto(vendaCompra.getValorDesconto());
        contaReceber.setValorTotal(vendaCompra.getValorTotal());

        repositoryContaReceber.save(contaReceber);
    }
}
