-- TABLE AVALIACAO_PRODUTO

CREATE TRIGGER validaChavePessoaAvaliacaoProdutoUPDATE
    BEFORE UPDATE
    ON avaliacao_produto
    FOR EACH ROW
    EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaAvaliacaoProdutoINSERT
    BEFORE INSERT
    ON avaliacao_produto
    FOR EACH ROW
    EXECUTE PROCEDURE validaChavePessoa();


--  TABLE CONTA_PAGAR

CREATE TRIGGER validaChavePessoaContaPagarUPDATE
    BEFORE UPDATE
    ON conta_pagar
    FOR EACH ROW
    EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaContaPagarINSERT
    BEFORE INSERT
    ON conta_pagar
    FOR EACH ROW
    EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaFornecedorContaPagarUPDATE
    BEFORE UPDATE
    ON conta_pagar
    FOR EACH ROW
EXECUTE PROCEDURE validachavepessoafornecedor();

CREATE TRIGGER validaChavePessoaFornecedorContaPagarINSERT
    BEFORE INSERT
    ON conta_pagar
    FOR EACH ROW
EXECUTE PROCEDURE validachavepessoafornecedor();

--  TABLE CONTA_RECEBER

CREATE TRIGGER validaChavePessoaContaReceberUPDATE
    BEFORE UPDATE
    ON conta_receber
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaContaReceberINSERT
    BEFORE INSERT
    ON conta_receber
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


--  TABLE ENDERECO


CREATE TRIGGER validaChavePessoaEnderecoUPDATE
    BEFORE UPDATE
    ON endereco
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaEnderecoINSERT
    BEFORE INSERT
    ON endereco
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


--  TABLE NOTA_FISCAL_COMPRA

CREATE TRIGGER validaChavePessoaNotaFiscalCompraUPDATE
    BEFORE UPDATE
    ON nota_fiscal_compra
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaNotaFiscalCompraINSERT
    BEFORE INSERT
    ON nota_fiscal_compra
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


--  TABLE USUARIO


CREATE TRIGGER validaChavePessoaUsuarioUPDATE
    BEFORE UPDATE
    ON usuario
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaUsuarioINSERT
    BEFORE INSERT
    ON usuario
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();


--  TABLE VENDA_COMPRA


CREATE TRIGGER validaChavePessoaVendaCompraUPDATE
    BEFORE UPDATE
    ON venda_compra
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();

CREATE TRIGGER validaChavePessoaVendaCompraINSERT
    BEFORE INSERT
    ON venda_compra
    FOR EACH ROW
EXECUTE PROCEDURE validaChavePessoa();
