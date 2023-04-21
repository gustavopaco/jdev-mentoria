package com.pacoprojects.api.integration.juno;

import com.pacoprojects.api.integration.juno.webhook.receber.notificacao.ResponseWebHookReceberNotificacaoDataDto;
import com.pacoprojects.api.integration.juno.webhook.receber.notificacao.ResponseWebHookReceberNotificacaoDto;
import com.pacoprojects.model.BoletoJuno;
import com.pacoprojects.repository.BoletoJunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiJunoWebHookService {

    private final BoletoJunoRepository repositoryBoletoJuno;


    public void receberNotificacaoPagamento(ResponseWebHookReceberNotificacaoDto notificacaoDto) {

        for (ResponseWebHookReceberNotificacaoDataDto dataDto : notificacaoDto.data()) {
            boolean isBoletoPago = dataDto.attributes().status().equals("CONFIRMED");
            Optional<BoletoJuno> optionalBoletoJuno = repositoryBoletoJuno.findByIdChrBoleto(dataDto.attributes().charge().id());

            if (optionalBoletoJuno.isPresent()) {
                if (!optionalBoletoJuno.get().isQuitado() && isBoletoPago) {
                    repositoryBoletoJuno.updateBoletoStatusQuitadoTrue(optionalBoletoJuno.get().getId());
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
