CREATE SEQUENCE IF NOT EXISTS sequence_access_token_juno_api START WITH 1 INCREMENT BY 1;

CREATE TABLE access_token_juno_api
(
    id            BIGINT NOT NULL,
    access_token  TEXT,
    token_type    VARCHAR(255),
    expires_in    INTEGER,
    scope         VARCHAR(255),
    user_name     VARCHAR(255),
    jti           VARCHAR(255),
    data_cadastro TIMESTAMP WITHOUT TIME ZONE,
    token_acesso  VARCHAR(255),
    CONSTRAINT pk_access_token_juno_api PRIMARY KEY (id)
);
