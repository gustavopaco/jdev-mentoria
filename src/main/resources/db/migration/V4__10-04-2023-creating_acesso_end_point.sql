CREATE SEQUENCE IF NOT EXISTS sequence_acesso_end_point START WITH 1 INCREMENT BY 1;

CREATE TABLE acesso_end_point
(
    id         BIGINT       NOT NULL,
    nome       VARCHAR(255) NOT NULL,
    quantidade DECIMAL      NOT NULL,
    CONSTRAINT pk_acesso_end_point PRIMARY KEY (id)
);

ALTER TABLE acesso_end_point
    ADD CONSTRAINT unique_nome UNIQUE (nome);

ALTER TABLE conta_pagar
ALTER
COLUMN valor_desconto TYPE DECIMAL USING (valor_desconto::DECIMAL);

ALTER TABLE conta_receber
ALTER
COLUMN valor_desconto TYPE DECIMAL USING (valor_desconto::DECIMAL);

ALTER TABLE nota_fiscal_compra
ALTER
COLUMN valor_desconto TYPE DECIMAL USING (valor_desconto::DECIMAL);

ALTER TABLE venda_compra
ALTER
COLUMN valor_desconto TYPE DECIMAL USING (valor_desconto::DECIMAL);

ALTER TABLE venda_compra
ALTER
COLUMN valor_frete TYPE DECIMAL USING (valor_frete::DECIMAL);

ALTER TABLE nota_fiscal_compra
ALTER
COLUMN valor_icms TYPE DECIMAL USING (valor_icms::DECIMAL);

ALTER TABLE cupom_desconto
ALTER
COLUMN valor_porcentagem_descricao TYPE DECIMAL USING (valor_porcentagem_descricao::DECIMAL);

ALTER TABLE cupom_desconto
ALTER
COLUMN valor_real_desconto TYPE DECIMAL USING (valor_real_desconto::DECIMAL);

ALTER TABLE conta_pagar
ALTER
COLUMN valor_total TYPE DECIMAL USING (valor_total::DECIMAL);

ALTER TABLE conta_receber
ALTER
COLUMN valor_total TYPE DECIMAL USING (valor_total::DECIMAL);

ALTER TABLE nota_fiscal_compra
ALTER
COLUMN valor_total TYPE DECIMAL USING (valor_total::DECIMAL);

ALTER TABLE venda_compra
ALTER
COLUMN valor_total TYPE DECIMAL USING (valor_total::DECIMAL);

ALTER TABLE produto
ALTER
COLUMN valor_venda TYPE DECIMAL USING (valor_venda::DECIMAL);
