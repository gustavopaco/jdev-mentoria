package com.pacoprojects.view;

import com.pacoprojects.api.integration.juno.ApiJunoCobrancaService;
import com.pacoprojects.api.integration.juno.cobranca.criar.cartao.StatusCartaoJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request.RequestCartaoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "mvc")
@RequiredArgsConstructor
public class MvcController {

    private final MvcService serviceMvc;
    private final ApiJunoCobrancaService serviceCobrancaJuno;

    @GetMapping(path = "pagamento/{idVenda}")
    public ModelAndView carregarVendaNaTela(@PathVariable(name = "idVenda", required = false) String idVenda) {
        return serviceMvc.getVenda(idVenda);
    }

    @PostMapping(path = "realizarVendaCartao")
    public ResponseEntity<StatusCartaoJunoDto>  realizarVendaCartao(@Valid @RequestBody RequestCartaoDto requestCartaoDto) {
        return ResponseEntity.ok(serviceCobrancaJuno.apiGerarCobrancaCartao(requestCartaoDto));
    }
}
