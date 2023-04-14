ALTER TABLE venda_compra
    ADD status_venda_compra VARCHAR(255);

UPDATE venda_compra
SET status_venda_compra = 'n/a'
WHERE status_venda_compra IS NULL;
ALTER TABLE venda_compra
    ALTER COLUMN status_venda_compra SET NOT NULL;

