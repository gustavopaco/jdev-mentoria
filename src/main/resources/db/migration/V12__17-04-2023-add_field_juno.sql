ALTER TABLE access_token_juno_api
    ADD token_autenticacao VARCHAR(255);

ALTER TABLE access_token_juno_api
    DROP COLUMN token_acesso;
