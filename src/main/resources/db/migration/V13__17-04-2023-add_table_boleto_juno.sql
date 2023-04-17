CREATE SEQUENCE IF NOT EXISTS sequence_boleto_juno START WITH 1 INCREMENT BY 1;

CREATE TABLE boleto_juno
(
    id                 BIGINT  NOT NULL,
    code               BIGINT,
    link               VARCHAR(255),
    checkout_url       VARCHAR(255),
    quitado            BOOLEAN NOT NULL,
    data_vencimento    TIMESTAMP WITHOUT TIME ZONE,
    valor              numeric,
    recorrencia        INTEGER,
    id_chr_boleto      VARCHAR(255),
    installment_link   VARCHAR(255),
    id_pix             VARCHAR(255),
    payload_in_base_64 TEXT,
    image_in_base_64   TEXT,
    charge_i_cartao    VARCHAR(255),
    venda_compra_id    BIGINT,
    empresa_id         BIGINT,
    CONSTRAINT pk_boletojuno PRIMARY KEY (id)
);

ALTER TABLE boleto_juno
    ADD CONSTRAINT EMPRESA_ID_FKQxZ5GF FOREIGN KEY (empresa_id) REFERENCES pessoa_juridica (pessoa_id);

ALTER TABLE boleto_juno
    ADD CONSTRAINT VENDA_COMPRA_ID_FKIAszHF FOREIGN KEY (venda_compra_id) REFERENCES venda_compra (id);
