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

    public static String getMandatoryPasswordChangeMessage(String nomeDoUsuario) {
        StringBuilder builder = new StringBuilder();
        builder.append("Olá, ").append(nomeDoUsuario).append("<br/>");
        builder.append("Está no momento de trocar sua senha, já passou os 90 dias de validade. ").append("<br/>");
        builder.append("Troque sua senha da loja virtual https://www.pacoprojects.com/jdev-mentoria");
        return builder.toString();
    }

    public static String getServerErrorMessage(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Olá Admin, ocorreu um erro inesperado no servidor</b>").append("<br/>").append("<br/>");
        builder.append(message);
        return builder.toString();
    }
}
