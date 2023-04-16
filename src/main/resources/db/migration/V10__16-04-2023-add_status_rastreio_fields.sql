ALTER TABLE status_rastreio
    ADD url_rastreio VARCHAR(255);

ALTER TABLE status_rastreio
    DROP COLUMN centro_distribuicao;

ALTER TABLE status_rastreio
    DROP COLUMN cidade;

ALTER TABLE status_rastreio
    DROP COLUMN estado;

ALTER TABLE status_rastreio
    DROP COLUMN status;
