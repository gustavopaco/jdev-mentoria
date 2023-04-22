package com.pacoprojects.view;

import com.pacoprojects.dto.projections.VendaCompraProjectionSelected;
import com.pacoprojects.service.VendaCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class MvcService {

    private final VendaCompraService serviceVendaCompra;

    public ModelAndView getVenda(String idVenda) {
        ModelAndView modelAndView = new ModelAndView("pagamento");
        VendaCompraProjectionSelected vendaCompra = serviceVendaCompra.getVendaCompraById(Long.parseLong(idVenda));
        modelAndView.addObject("venda", vendaCompra);
        return modelAndView;
    }
}
