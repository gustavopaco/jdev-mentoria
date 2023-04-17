package com.pacoprojects.api.integration.juno;

// Dados que irao vir da tela para ser enviado para Api
public record CobrancaJunoDto(

        // Descricao da cobranca
        String description,

        // Nome do comprador ou cliente
        String payerName,

        // Telefone do comprador ou cliente
        String payerPhone,

        // Quantidade de parcelas
        String installMents,

        // Referencia para algum produto da loja ou codigo do produto, poderia ser o codigo da venda
        String reference,

        // Se for parcelado, quantidade de parcelas
        Integer recurrency
) {
}
