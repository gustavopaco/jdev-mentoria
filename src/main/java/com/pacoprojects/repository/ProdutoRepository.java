package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.ProdutoProjections;
import com.pacoprojects.model.Produto;
import com.pacoprojects.report.ReportProdutoLowStockProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNomeIgnoreCaseAndEmpresa_Id(String nome, Long id);

    List<ProdutoProjections> findAllByEmpresa_Id(Long idEmpresa);

    List<ProdutoProjections> findAllByNomeContainsIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);

    Optional<ProdutoProjections> findProdutoById(Long id);

    @Query(nativeQuery = true,
            value = """
                    SELECT emp.id                         as codigoDonoEmpresa,
                           emp.nome                       as nomeDonoEmpresa,
                           emp.email                      as emailDonoEmpresa,
                           prod.id                        as codigoProduto,
                           prod.nome                      as produtoNome,
                           prod.quantidade_estoque        as quantidadeEstoque,
                           prod.quantidade_alerta_estoque as quantidadeAlertaEstoque,
                           forne.id                       as codigoFornecedor,
                           forne.nome                     as nomeFornecedor,
                           forne.email                    as emailFornecedor
                    FROM produto prod
                            inner join item_nota_produto inp on prod.id = inp.produto_id
                            inner join nota_fiscal_compra nfc on inp.nota_fiscal_compra_id = nfc.id
                            inner join pessoa forne on forne.id = nfc.pessoa_id
                            inner join pessoa emp on emp.id = prod.empresa_id
                            where prod.alerta_estoque_enabled = true
                            and prod.quantidade_estoque < prod.quantidade_alerta_estoque
                            and ( :idEmpresa IS NULL OR emp.id =:idEmpresa )""")
    List<ReportProdutoLowStockProjection> relatorioProdutoEstoqueBaixo(@Param("idEmpresa") Long idEmpresa);
}
