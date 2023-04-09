package com.pacoprojects.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Override
    @Async
    public void sendSimpleMail(EmailObject emailObject) {

        try {
            // Creating a simple mail message
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            // Setting up necessary details
            // Remetente:
            simpleMailMessage.setFrom(remetente);

            // Destinatario
            simpleMailMessage.setTo(emailObject.destinatario());

            // Assunto do Email
            simpleMailMessage.setSubject(emailObject.assunto());

            // Corpo do Email
            simpleMailMessage.setText(emailObject.menssagem());

            // Sending the mail
            javaMailSender.send(simpleMailMessage);

            // Metodo deve ser VOID para conseguir funcionar de modo Asyncrono
        } catch (Exception exception) {
            LOGGER.error("Falha ao enviar E-mail", exception);
            throw new IllegalStateException("Falha ao enviar Email");
        }
    }

    @Override
    @Async
    public void sendMailWithAttachment(EmailObject emailObject) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // Setting multipart as true for attachments to be send
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            // Setting up necessary details
            // Remetente:
            helper.setFrom(remetente);

            // Destinatario
            helper.setTo(emailObject.destinatario());

            // Assunto do Email
            helper.setSubject(emailObject.assunto());

            // Corpo do Email
            helper.setText(emailObject.menssagem(), true);

            // Adding the attachment
            if (emailObject.anexo() != null && !emailObject.anexo().isBlank()) {
                FileSystemResource file = new FileSystemResource(emailObject.anexo());
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            // Sending the mail
            javaMailSender.send(mimeMessage);

            // Metodo deve ser VOID para conseguir funcionar de modo Asyncrono
        } catch (MessagingException exception) {
            LOGGER.error("Falha ao enviar E-mail", exception);
            throw new IllegalStateException("Falha ao enviar E-mail");
        }
    }
}
