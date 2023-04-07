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


--
