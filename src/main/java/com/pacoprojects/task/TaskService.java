package com.pacoprojects.task;

import com.pacoprojects.email.EmailMessage;
import com.pacoprojects.email.EmailObject;
import com.pacoprojects.email.EmailService;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UsuarioRepository repositoryUsuario;
    private final EmailService emailService;

    // Note: Calculo de Milisegundos 24(Qtde Horas) * 60(Qtas minutos em 1 hora) * 60(Qtde de segundos em 1 minuto) * 1000(Qtde de milisegundos em 1 segundo)
//    @Scheduled(initialDelay = 2000, fixedDelay = 24 * 60 * 60 * 1000)

    // Note: a Tarefa "cron" eh outra forma de fazer a mesma coisa, task com intervalo para isso, definimos:
    //  second, minute, hour, day of month, month, and day of week.
    //  O codigo abaixo ira executar a cada: (0 segundos, 0minutos, 11horas, *QualquerDia, *QualquerMes, *QualquerDiaDaSemana)
    //  e no TimeZone de Sao_Paulo
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")   // Roda todos dia as 11 da manha
    public void notifyUserToChangePassword() {

        Set<Usuario> usuarios = repositoryUsuario.findUsuariosByDateLastPasswordChangeBefore(LocalDateTime.now().minusDays(90));

        usuarios.forEach(user -> {
            emailService
                    .sendMailWithAttachment(EmailObject
                            .builder()
                            .destinatario(user.getUsername())
                            .assunto("Altera sua senha.")
                            .menssagem(EmailMessage.getMandatoryPasswordChangeMessage(user.getPessoa().getNome()))
                            .build());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
