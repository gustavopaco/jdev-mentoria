package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.NotaFiscalCompraProjections;
import com.pacoprojects.model.NotaFiscalCompra;
import com.pacoprojects.report.ReportNotaFiscalProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {

    boolean existsByDescricaoIgnoreCaseAndPessoa_Id(String descricao, Long id);

    List<NotaFiscalCompraProjections> findAllByDescricaoContainsIgnoreCase(String descricao);

    List<NotaFiscalCompraProjections> findAllByPessoa_Id(Long idPessoa);

    List<NotaFiscalCompraProjections> findAllByContaPagar_Id(Long idContaPagar);

    List<NotaFiscalCompraProjections> findAllByEmpresa_Id(Long idEmpresa);

    Optional<NotaFiscalCompraProjections> findNotaFiscalCompraById(Long id);

    @Query(value =
            " SELECT fornecedor.id AS codigoFornecedor, " +
            "       fornecedor.nome AS nomeFornecedor, " +
            "       notaFiscal.id AS codigoNotaFiscal, " +
            "       notaFiscal.dataCompra AS dataCompra, " +
            "       inp.quantidade AS quantidadeComprado, " +
            "       produto.id AS codigoProduto, " +
            "       produto.nome AS nomeProduto, " +
            "       produto.valorVenda AS valorVendaProduto " +
            " FROM NotaFiscalCompra notaFiscal " +
            " JOIN notaFiscal.pessoa fornecedor " +
            " JOIN ItemNotaProduto inp on inp.notaFiscalCompra.id = notaFiscal.id " +
            " JOIN inp.produto produto " +
            " WHERE notaFiscal.dataCompra between :dataInicial and :dataFinal " +
            " and ( :codigoProduto IS NULL OR produto.id =:codigoProduto ) " +
            " and ( :nomeProduto IS NULL OR produto.nome ilike %:nomeProduto%) " +
            " and ( :codigoFornecedor IS NULL OR fornecedor.id =:codigoFornecedor ) " +
            " and ( :codigoNota IS NULL OR notaFiscal.id =:codigoNota) " )
    List<ReportNotaFiscalProjection> relatorioProdutoPorNotaFiscalJPQL(
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal,
            @Param("codigoProduto") Long codigoProduto,
            @Param("nomeProduto") String nomeProduto,
            @Param("codigoFornecedor") Long codigoFornecedor,
            @Param("codigoNota") Long codigoNota
    );


    @Query(
            nativeQuery = true,
            value = """ 
                    SELECT prod.id                  as codigoProduto,
                           prod.nome                as nomeProduto,
                           prod.valor_venda         as valorVendaProduto,
                           inp.quantidade           as quantidadeComprado,
                           fornecedor.id            as codigoFornecedor,
                           fornecedor.nome          as nomeFornecedor,
                           notaFiscal.data_compra   as dataCompra
                    FROM nota_fiscal_compra as notaFiscal
                             inner join pessoa fornecedor on fornecedor.id = notaFiscal.pessoa_id
                             inner join item_nota_produto inp on notaFiscal.id = inp.nota_fiscal_compra_id
                             inner join produto prod on prod.id = inp.produto_id
                             where notaFiscal.data_compra between :dataInicial and :dataFinal
                             and ( :codigoProduto IS NULL OR prod.id =:codigoProduto )
                             and ( :nomeProduto IS NULL OR prod.nome ilike concat('%',:nomeProduto,'%') )
                             and ( :codigoFornecedor IS NULL OR fornecedor.id =:codigoFornecedor )
                             and ( :codigoNota IS NULL OR notaFiscal.id =:codigoNota )""")
    List<ReportNotaFiscalProjection> relatorioProdutoPorNotaFiscal(
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal,
            @Param("codigoProduto") Long codigoProduto,
            @Param("nomeProduto") String nomeProduto,
            @Param("codigoFornecedor") Long codigoFornecedor,
            @Param("codigoNota") Long codigoNota
    );

}
