package com.pacoprojects.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "mvc")
@RequiredArgsConstructor
public class MvcController {

    private final MvcService serviceMvc;

    @GetMapping(path = "pagamento/{idVenda}")
    public ModelAndView pagamento(@PathVariable(name = "idVenda", required = false) String idVenda) {
        return serviceMvc.getVenda(idVenda);
    }
}
