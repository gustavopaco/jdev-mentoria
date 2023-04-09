package com.pacoprojects.email;

import lombok.Builder;

@Builder
public record EmailObject(

        String destinatario,
        String assunto,
        String menssagem,
        String anexo
) {
}
