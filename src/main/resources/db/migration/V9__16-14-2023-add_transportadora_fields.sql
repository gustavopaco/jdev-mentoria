ALTER TABLE venda_compra
    ADD codigo_etiqueta VARCHAR(255);

ALTER TABLE venda_compra
    ADD servico_transportadora INTEGER;

ALTER TABLE venda_compra
    ADD url_etiqueta VARCHAR(255);

ALTER TABLE endereco
    ADD pais VARCHAR(255);
