-- TRIGGER PESSOA para TABLE Conta_pagar "pessoa_id"

CREATE
    OR REPLACE FUNCTION validaChavePessoa()
    RETURNS TRIGGER
    language plpgsql as
$$
DECLARE
    existe integer;

BEGIN
    existe = (SELECT count(1) FROM pessoa WHERE id = NEW.pessoa_id);
    if (existe <= 0) then
        RAISE EXCEPTION 'Não foi encontrado o ID ou PK da pessoa para realizar associação';
    end if;
    RETURN NEW;
END;
$$;


--  TRIGGER FORNECEDOR para TABLE Conta_Pagar "pessoa_fornecedor_id"


CREATE
    OR REPLACE FUNCTION validaChavePessoaFornecedor()
    RETURNS TRIGGER
    language plpgsql as
$$
DECLARE
    existe integer;

BEGIN
    existe = (SELECT count(1) FROM pessoa WHERE id = NEW.pessoa_fornecedor_id);
    if (existe <= 0) then
        RAISE EXCEPTION 'Não foi encontrado o ID ou PK da pessoa para realizar associação';
    end if;
    RETURN NEW;
END;
$$;
