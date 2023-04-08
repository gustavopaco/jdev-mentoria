CREATE SEQUENCE IF NOT EXISTS sequence_avaliacao_produto START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_categoria START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_conta_pagar START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_conta_receber START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_cupom_desconto START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_endereco START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_forma_pagamento START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_imagem_produto START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_item_nota_produto START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_item_venda_compra START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_marca_produto START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_nota_fiscal_compra START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_nota_fiscal_venda START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_pessoa START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_produto START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_role START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_status_rastreio START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_telefone START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_usuario START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_venda_compra START WITH 1 INCREMENT BY 1;

CREATE TABLE avaliacao_produto
(
    id         BIGINT       NOT NULL,
    descricao  VARCHAR(255) NOT NULL,
    nota       INTEGER      NOT NULL,
    produto_id BIGINT,
    pessoa_id  BIGINT,
    CONSTRAINT pk_avaliacao_produto PRIMARY KEY (id)
);

CREATE TABLE categoria
(
    id   BIGINT       NOT NULL,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categoria PRIMARY KEY (id)
);

CREATE TABLE categoria_produto
(
    categoria_id BIGINT NOT NULL,
    produto_id   BIGINT NOT NULL,
    CONSTRAINT pk_categoria_produto PRIMARY KEY (categoria_id, produto_id)
);

CREATE TABLE conta_pagar
(
    id                   BIGINT       NOT NULL,
    descricao            VARCHAR(255) NOT NULL,
    status               VARCHAR(255) NOT NULL,
    data_vencimento      date         NOT NULL,
    data_pagamento       date,
    valor_total          DECIMAL      NOT NULL,
    valor_desconto       DECIMAL,
    pessoa_id            BIGINT,
    pessoa_fornecedor_id BIGINT,
    CONSTRAINT pk_conta_pagar PRIMARY KEY (id)
);

CREATE TABLE conta_receber
(
    id              BIGINT       NOT NULL,
    descricao       VARCHAR(255) NOT NULL,
    status          VARCHAR(255) NOT NULL,
    data_vencimento date         NOT NULL,
    data_pagamento  date,
    valor_total     DECIMAL      NOT NULL,
    valor_desconto  DECIMAL,
    pessoa_id       BIGINT,
    CONSTRAINT pk_conta_receber PRIMARY KEY (id)
);

CREATE TABLE cupom_desconto
(
    id                          BIGINT       NOT NULL,
    codigo_descricao            VARCHAR(255) NOT NULL,
    valor_real_desconto         DECIMAL,
    valor_porcentagem_descricao DECIMAL,
    validade_cupom              date         NOT NULL,
    CONSTRAINT pk_cupom_desconto PRIMARY KEY (id)
);

CREATE TABLE endereco
(
    id            BIGINT       NOT NULL,
    rua           VARCHAR(255) NOT NULL,
    cep           VARCHAR(255) NOT NULL,
    numero        VARCHAR(255) NOT NULL,
    complemento   VARCHAR(255),
    bairro        VARCHAR(255) NOT NULL,
    cidade        VARCHAR(255) NOT NULL,
    estado        VARCHAR(255) NOT NULL,
    tipo_endereco VARCHAR(255) NOT NULL,
    pessoa_id     BIGINT,
    CONSTRAINT pk_endereco PRIMARY KEY (id)
);

CREATE TABLE forma_pagamento
(
    id        BIGINT       NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    CONSTRAINT pk_forma_pagamento PRIMARY KEY (id)
);

CREATE TABLE imagem_produto
(
    id               BIGINT NOT NULL,
    imagem_original  TEXT   NOT NULL,
    imagem_miniatura TEXT   NOT NULL,
    produto_id       BIGINT,
    CONSTRAINT pk_imagem_produto PRIMARY KEY (id)
);

CREATE TABLE item_nota_produto
(
    id                    BIGINT           NOT NULL,
    quantidade            DOUBLE PRECISION NOT NULL,
    produto_id            BIGINT,
    nota_fiscal_compra_id BIGINT,
    CONSTRAINT pk_item_nota_produto PRIMARY KEY (id)
);

CREATE TABLE item_venda_compra
(
    id              BIGINT           NOT NULL,
    quantidade      DOUBLE PRECISION NOT NULL,
    produto_id      BIGINT,
    venda_compra_id BIGINT,
    CONSTRAINT pk_item_venda_compra PRIMARY KEY (id)
);

CREATE TABLE marca_produto
(
    id   BIGINT       NOT NULL,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT pk_marca_produto PRIMARY KEY (id)
);

CREATE TABLE nota_fiscal_compra
(
    id             BIGINT       NOT NULL,
    numero         VARCHAR(255) NOT NULL,
    serie          VARCHAR(255) NOT NULL,
    descricao      VARCHAR(255),
    valor_total    DECIMAL      NOT NULL,
    valor_desconto DECIMAL,
    valor_icms     DECIMAL      NOT NULL,
    data_compra    date         NOT NULL,
    pessoa_id      BIGINT,
    conta_pagar_id BIGINT,
    CONSTRAINT pk_nota_fiscal_compra PRIMARY KEY (id)
);

CREATE TABLE nota_fiscal_venda
(
    id              BIGINT       NOT NULL,
    numero          VARCHAR(255) NOT NULL,
    serie           VARCHAR(255) NOT NULL,
    tipo            VARCHAR(255) NOT NULL,
    xml             TEXT         NOT NULL,
    pdf             TEXT         NOT NULL,
    venda_compra_id BIGINT,
    CONSTRAINT pk_nota_fiscal_venda PRIMARY KEY (id)
);

CREATE TABLE pessoa
(
    id    BIGINT       NOT NULL,
    nome  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_pessoa PRIMARY KEY (id)
);

CREATE TABLE pessoa_fisica
(
    pessoa_id       BIGINT       NOT NULL,
    cpf             VARCHAR(255) NOT NULL,
    data_nascimento date,
    CONSTRAINT pk_pessoa_fisica PRIMARY KEY (pessoa_id)
);

CREATE TABLE pessoa_juridica
(
    pessoa_id           BIGINT       NOT NULL,
    cnpj                VARCHAR(255) NOT NULL,
    inscricao_estadual  VARCHAR(255) NOT NULL,
    inscricao_municipal VARCHAR(255),
    nome_fantasia       VARCHAR(255) NOT NULL,
    razao_social        VARCHAR(255) NOT NULL,
    categoria           VARCHAR(255),
    CONSTRAINT pk_pessoa_juridica PRIMARY KEY (pessoa_id)
);

CREATE TABLE produto
(
    id                        BIGINT           NOT NULL,
    tipo_unidade              VARCHAR(255)     NOT NULL,
    nome                      VARCHAR(255)     NOT NULL,
    descricao                 TEXT,
    peso                      DOUBLE PRECISION NOT NULL,
    largura                   DOUBLE PRECISION NOT NULL,
    altura                    DOUBLE PRECISION NOT NULL,
    profundidade              DOUBLE PRECISION NOT NULL,
    valor_venda               DECIMAL          NOT NULL,
    quantidade_estoque        INTEGER          NOT NULL,
    quantidade_alerta_estoque INTEGER,
    link_youtube              VARCHAR(255),
    quantidade_click          INTEGER,
    alerta_estoque_enabled    BOOLEAN,
    enabled                   BOOLEAN          NOT NULL,
    marca_id                  BIGINT,
    CONSTRAINT pk_produto PRIMARY KEY (id)
);

CREATE TABLE role
(
    id        BIGINT       NOT NULL,
    authority VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE status_rastreio
(
    id                  BIGINT NOT NULL,
    centro_distribuicao VARCHAR(255),
    cidade              VARCHAR(255),
    estado              VARCHAR(255),
    status              VARCHAR(255),
    venda_compra_id     BIGINT,
    CONSTRAINT pk_status_rastreio PRIMARY KEY (id)
);

CREATE TABLE telefone
(
    id        BIGINT       NOT NULL,
    numero    VARCHAR(255) NOT NULL,
    pessoa_id BIGINT,
    CONSTRAINT pk_telefone PRIMARY KEY (id)
);

CREATE TABLE usuario
(
    id                        BIGINT       NOT NULL,
    username                  VARCHAR(255) NOT NULL,
    password                  VARCHAR(255) NOT NULL,
    date_last_password_change TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    jwt                       TEXT,
    enabled                   BOOLEAN,
    pessoa_id                 BIGINT,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE TABLE usuario_role
(
    role_id    BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT pk_usuario_role PRIMARY KEY (role_id, usuario_id)
);

CREATE TABLE venda_compra
(
    id                   BIGINT  NOT NULL,
    valor_total          DECIMAL NOT NULL,
    valor_desconto       DECIMAL,
    valor_frete          DECIMAL NOT NULL,
    dia_entrega          INTEGER NOT NULL,
    data_venda           date    NOT NULL,
    data_entrega         date    NOT NULL,
    pessoa_id            BIGINT,
    endereco_entrega_id  BIGINT,
    endereco_cobranca_id BIGINT,
    forma_pagamento_id   BIGINT,
    nota_fiscal_venda_id BIGINT,
    cupom_desconto_id    BIGINT,
    CONSTRAINT pk_venda_compra PRIMARY KEY (id)
);

ALTER TABLE nota_fiscal_compra
    ADD CONSTRAINT CONTA_PAGAR_ID_FK FOREIGN KEY (conta_pagar_id) REFERENCES conta_pagar (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT CUPOM_DESCONTO_ID_FK FOREIGN KEY (cupom_desconto_id) REFERENCES cupom_desconto (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT ENDERECO_COBRANCA_ID_FK FOREIGN KEY (endereco_cobranca_id) REFERENCES endereco (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT ENDERECO_ENTREGA_ID_FK FOREIGN KEY (endereco_entrega_id) REFERENCES endereco (id);

ALTER TABLE pessoa_fisica
    ADD CONSTRAINT FK_PESSOA_FISICA_ON_PESSOA FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE pessoa_juridica
    ADD CONSTRAINT FK_PESSOA_JURIDICA_ON_PESSOA FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT FORMA_PAGAMENTO_ID_FK FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);

ALTER TABLE produto
    ADD CONSTRAINT MARCA_ID_FK FOREIGN KEY (marca_id) REFERENCES marca_produto (id);

ALTER TABLE item_nota_produto
    ADD CONSTRAINT NOTA_FISCAL_COMPRA_ID_FK FOREIGN KEY (nota_fiscal_compra_id) REFERENCES nota_fiscal_compra (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT NOTA_FISCAL_VENDA_ID_FK FOREIGN KEY (nota_fiscal_venda_id) REFERENCES nota_fiscal_venda (id);

ALTER TABLE conta_pagar
    ADD CONSTRAINT PESSOA_FORNECEDOR_ID_FK FOREIGN KEY (pessoa_fornecedor_id) REFERENCES pessoa (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT PESSOA_ID_FK FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT PESSOA_ID_FK FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT PESSOA_ID_FK FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE venda_compra
    ADD CONSTRAINT PESSOA_ID_FK FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE conta_pagar
    ADD CONSTRAINT PESSOA_ID_FK3mzc5V FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE usuario
    ADD CONSTRAINT PESSOA_ID_FKEhV632 FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE avaliacao_produto
    ADD CONSTRAINT PESSOA_ID_FKHQA7gp FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE conta_receber
    ADD CONSTRAINT PESSOA_ID_FKt91hpX FOREIGN KEY (pessoa_id) REFERENCES pessoa (id);

ALTER TABLE item_nota_produto
    ADD CONSTRAINT PRODUTO_ID_FK FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE avaliacao_produto
    ADD CONSTRAINT PRODUTO_ID_FK0FBeDL FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE imagem_produto
    ADD CONSTRAINT PRODUTO_ID_FK85SpYU FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE item_venda_compra
    ADD CONSTRAINT PRODUTO_ID_FKHxkyi6 FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE status_rastreio
    ADD CONSTRAINT VENDA_COMPRA_ID_FK FOREIGN KEY (venda_compra_id) REFERENCES venda_compra (id);

ALTER TABLE status_rastreio
    ADD CONSTRAINT VENDA_COMPRA_ID_FK FOREIGN KEY (venda_compra_id) REFERENCES venda_compra (id);

ALTER TABLE item_venda_compra
    ADD CONSTRAINT VENDA_COMPRA_ID_FKEQlCPy FOREIGN KEY (venda_compra_id) REFERENCES venda_compra (id);

ALTER TABLE categoria_produto
    ADD CONSTRAINT categoria_id_fk FOREIGN KEY (categoria_id) REFERENCES categoria (id);

ALTER TABLE categoria_produto
    ADD CONSTRAINT categoria_id_fk6t0MXQ FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE usuario_role
    ADD CONSTRAINT usuario_id_fk FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE usuario_role
    ADD CONSTRAINT usuario_id_fk4eA4Ch FOREIGN KEY (role_id) REFERENCES role (id);
