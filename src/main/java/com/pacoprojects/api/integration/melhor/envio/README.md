# ORDEM PARA IMPLEMENTACAO DE INTERGRACAO COM MELHOR ENVIO TRANSPORTADORA


# Fazer a consulta do preco dos produtos
1) Passo 1
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/consulta/frete/exemploConsultaFreteRequest.json
        2) Dto de Envio: | Pasta REQUEST/consulta/frete/MelhorEnvioConsultaFreteRequestDto
        3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo consultMelhorEnvioFrete
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/consulta/frete/exemploConsultaFreteResponse.json
        2) Dto de Recebimento: | Pasta RESPONSE/consulta/frete/MelhorEnvioConsultaFreteResponseDto
        3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo consultMelhorEnvioFrete


# Inserir frete no carrinho
1) Passo 2
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/inserir/frete/carrinho/exemploInserirFreteRequest.json
        2) Dto de Envio: | Pasta REQUEST/inserir/frete/carrinho/MelhorEnvioInserirCarrinhoDtoRequestDto
        3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/inserir/frete/carrinho/exemploInserirFreteResponse.json
        2) Dto de Recebimento: | Pasta RESPONSE/frete/frete/carrinho/MelhorEnvioInserirFreteCarrinhoResponseDto
        3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO


# Fazer checkout do carrinho com os fretes
1) Passo 3
   1) REQUEST
      1) Exemplo json | Pasta REQUEST/checkout/frete/exemploCheckoutRequest.json
      2) Dto de Envio: | Pasta REQUEST/checkout/frete/MelhorEnvioCheckoutFreteRequestDto
      3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO
   2) RESPONSE
       1) Exemplo json | Pasta RESPONSE/checkout/frete/exemploCheckoutFreteResponse.json
       2) Dto de Recebimento: | Pasta RESPONSE/checkout/frete/MelhorEnvioCheckoutFreteResponseDto
       3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO


# Gerar codigo de rasteio(etiqueta)
1) Passo 4
    1) REQUEST
       1) Exemplo json | Pasta REQUEST/gerar/etiqueta/exemploGerarEtiquetaRequest.json
          2) Dto de Envio: | Pasta REQUEST/gerar/etiqueta/MelhorEnvioGerarEtiquetaRequestDto
          3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO
   2) RESPONSE
       1) Exemplo json | Pasta RESPONSE/gerar/etiqueta/exemploCheckoutFreteResponse.json
       2) Dto de Recebimento: | Nao existe Dto para recebe-lo pois ele ta retornando o numero do Pedido como nom do objeto
       3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO


# Imprimir etiqueta rasteio(etiqueta)
1) Passo 4
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/imprimir/etiqueta/ImprimirEtiquetaRequest.json
            2) Dto de Envio: | Pasta REQUEST/gerar/etiqueta/MelhorEnvioImprimirEtiquetaRequestDto
            3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/imprimir/etiqueta/exemploCheckoutFreteResponse.json
        2) Dto de Recebimento: | Pasta imprimir/etiqueta/MelhorEnvioImprimirEtiquetaResponseDto
        3) Implementacao: | Classe ApiConsultaMelhorEnvio metodo ESCREVER NOME METODO
