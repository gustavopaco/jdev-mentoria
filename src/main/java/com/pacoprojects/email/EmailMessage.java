package com.pacoprojects.email;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailMessage {

    public static String getDefaultMessage(String username, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Segue abaixo seus dados de acesso para a Loja Virtual.</b>").append("<br/>").append("<br/>");
        builder.append("<b>Login:</b>").append(username).append("<br/>");
        builder.append("<b>Senha:</b>").append(password).append("<br/>");
        builder.append("<br/>");
        builder.append("Obrigado por se registrar no nosso sistema!");

        return builder.toString();
    }
}
