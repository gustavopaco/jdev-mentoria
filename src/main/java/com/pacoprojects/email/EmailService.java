package com.pacoprojects.email;

public interface EmailService {

    void sendSimpleMail(EmailObject emailObject);

    void sendMailWithAttachment(EmailObject emailObject);
}
