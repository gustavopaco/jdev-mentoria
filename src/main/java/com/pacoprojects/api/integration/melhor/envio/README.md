# ORDEM PARA IMPLEMENTACAO DE INTERGRACAO COM MELHOR ENVIO TRANSPORTADORA


__________________________
Resumo dos tópicos principais para implementar: CONSULTA / INSERIR / CHECKOUT / GERAR / IMPRIMIR

Extras para mais funcionalidades: CANCELAR / RASTREAR

* Etiqueta: (Papel que a empresa cola na caixa ao enviar para transportadora)


* https://blog.melhorenvio.com.br/wp-content/uploads/2020/08/Etiqueta-Melhor-Envio-724x1024.png
__________________________

# Fazer a CONSULTA do preco dos produtos

1) Passo 1
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/consulta/frete/ExemploConsultaFreteRequest.json
        2) Dto de Envio: | Pasta REQUEST/consulta/frete/RequestMelhorEnvioConsultaFreteDto
  
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/consulta/frete/ExemploConsultaFreteResponse.json
        2) Dto de Recebimento: | Pasta RESPONSE/consulta/frete/ResponseMelhorEnvioConsultaFreteDto

    3) IMPLEMENTACAO:
        1) CLASSE "ApiConsultaMelhorEnvio" 
        2) METODO "apiConsultaFreteMelhorEnvio"
       
__________________________

# INSERIR frete no carrinho

1) Passo 2
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/inserir/frete/carrinho/ExemploInserirFreteRequest.json
        2) Dto de Envio: | Pasta REQUEST/inserir/frete/carrinho/RequestMelhorEnvioInserirCarrinhoDtoDto
       
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/inserir/frete/carrinho/ExemploInserirFreteResponse.json
        2) Dto de Recebimento: | Pasta RESPONSE/frete/frete/carrinho/ResponseMelhorEnvioInserirFreteCarrinhoDto

   3) IMPLEMENTACAO:
        1) CLASSE "ApiConsultaMelhorEnvio"
        2) METODO "apiInserirFreteCarrinhoMelhorEnvio"
      
__________________________

# Fazer CHECKOUT do carrinho com os fretes

1) Passo 3
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/checkout/frete/ExemploCheckoutFreteRequest.json
        2) Dto de Envio: | Pasta REQUEST/checkout/frete/RequestMelhorEnvioCheckoutFreteDto
       
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/checkout/frete/ExemploCheckoutFreteResponse.json
        2) Dto de Recebimento: | Pasta RESPONSE/checkout/frete/ResponseMelhorEnvioCheckoutFreteDto

   3) IMPLEMENTACAO:
        1) CLASSE "ApiConsultaMelhorEnvio"
        2) METODO "apiCheckoutFreteMelhorEnvio"

__________________________

# GERAR etiqueta

1) Passo 4
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/gerar/etiqueta/ExemploGerarEtiquetaRequest.json
        2) Dto de Envio: | Pasta REQUEST/gerar/etiqueta/RequestMelhorEnvioGerarEtiquetaDto
       
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/gerar/etiqueta/ExemploGerarEtiquetaResponse.json
        2) Dto de Recebimento: | Nao existe Dto para recebe-lo pois o retorno dele nao sera util

   3) IMPLEMENTACAO:
        1) CLASSE "ApiConsultaMelhorEnvio"
        2) METODO "apiGerarEtiquetaMelhorEnvio"

__________________________

# IMPRIMIR etiqueta

1) Passo 4
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/imprimir/etiqueta/ExemploImprimirEtiquetaRequest.json
        2) Dto de Envio: | Pasta REQUEST/gerar/etiqueta/RequestMelhorEnvioImprimirEtiquetaDto
       
    2) RESPONSE
        1) Exemplo json | Pasta RESPONSE/imprimir/etiqueta/ExemploImprimirEtiquetaResponse.json
        2) Dto de Recebimento: | Pasta imprimir/etiqueta/ResponseMelhorEnvioImprimirEtiquetaDto

   3) IMPLEMENTACAO:
        1) CLASSE "ApiConsultaMelhorEnvio"
        2) METODO "apiImprimeEtiquetaMelhorEnvio"

__________________________
FIM
__________________________

# Para Cancelar a etiqueta

1) Passo 1
    1) REQUEST
        1) Exemplo json | Pasta REQUEST/cancelamento/etiqueta/ExemploCancelamentoEtiqueta.json
        2) Dto de Envio: | Pasta REQUEST/cancelamento/etiqueta/RequestMelhorEnvioCancelamentoEtiquetaDto
        3) Implementacao: | Classe ApiConsultaMelhorEnvio METODO apiCancelarEtiquetaMelhorEnvio
       
   2) RESPONSE
        1) Não precisamos de nenhum dado do response, se o codigo for 200xx ja sabemos que foi cancelado a etiqueta

   3) IMPLEMENTACAO:
        1) CLASSE "ApiConsultaMelhorEnvio"
        2) METODO "apiCancelarEtiquetaMelhorEnvio"
      
__________________________
FIM
__________________________

# Para Gerar o rastreamento do pedido
1) Passo 1
    1) REQUEST
       1) Exemplo json | Pasta REQUEST/rastreio/pedido/ExemploRastreioRequest.json
       2) Dto de Envio: | Pasta REQUEST/rastreio/pedido/RequestMelhorEnvioRastreioPedidoDto
       
   2) RESPONSE
       1) Exemplo json | Pasta RESPONSE/rastreio/pedido/ExemploRastreioResponse.json
       2) Dto de Recebimento: | Pasta rastreio/pedido/ResponseMelhorEnvioRastreioPedidoDto
      
   3) IMPLEMENTACAO:
       1) CLASSE "ApiConsultaMelhorEnvio"
       2) METODO "apiGerarRastreioPedidoMelhorEnvio"
