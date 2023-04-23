package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.VendaCompraProjectionSelected;
import com.pacoprojects.enums.StatusVendaCompra;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.report.ReportVendaCanceladaProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VendaCompraRepository extends JpaRepository<VendaCompra, Long> {


    boolean findByIdAndEnabledTrue(Long idVenda);

    Optional<VendaCompraProjectionSelected> findVendaCompraById(@Param("id") Long id);

    List<VendaCompraProjectionSelected> findAllByEmpresa_Id(Long idEmpresa);

    @Modifying
    @Query(value = "UPDATE VendaCompra v set v.statusVendaCompra =:statusVendaCompra where v.id =:idVenda ")
    void updateStatusVendaFinalizada(@Param("idVenda") Long idVenda, @Param("statusVendaCompra") StatusVendaCompra statusVendaCompra);

    @Modifying
    @Query(value = "UPDATE VendaCompra set enabled = false where id = ?1")
    void softDelete(Long id);

    @Modifying
    @Query(value = "UPDATE VendaCompra set enabled = true where id = ?1")
    void enableVenda(Long id);

    @Modifying
    @Query(value = "update VendaCompra v set v.codigoEtiqueta =:codigoEtiqueta where v.id =:idVenda ")
    void updateCodigoEtiqueta(@Param("idVenda") Long idVenda, @Param("codigoEtiqueta") String codigoEtiqueta);

    @Modifying
    @Query(value = "update VendaCompra v set v.urlEtiqueta =:urlEtiqueta where v.id =:idVenda ")
    void updateUrlEtiqueta(@Param("idVenda") Long idVenda, @Param("urlEtiqueta") String urlEtiqueta);

    @Modifying
    @Query(nativeQuery = true,
            value = " DELETE FROM item_venda_compra WHERE venda_compra_id =:id ;" +
                    " UPDATE venda_compra v SET nota_fiscal_venda_id = NULL WHERE v.id = :id ; " +
                    " DELETE FROM nota_fiscal_venda WHERE venda_compra_id = :id ; " +
                    " DELETE FROM status_rastreio WHERE venda_compra_id = :id ; " +
                    " DELETE FROM venda_compra WHERE id = :id ; ")
    void deleteVendaCompraByIdNative(@Param("id") Long id);


    default void deleteVendaCompraAndAssociatedEntities(Long id) {
        queryDeleteItemVendaCompraById(id);
        queryUpdateVendaCompraById(id);
        queryDeleteNotaFiscalVendaById(id);
        queryDeleteStatusRastreioById(id);
        queryDeleteVendaCompraById(id);
    }

    @Modifying
    @Query(value = "DELETE FROM ItemVendaCompra i WHERE i.vendaCompra.id = :id ")
    void queryDeleteItemVendaCompraById(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE VendaCompra v SET v.notaFiscalVenda = NULL WHERE v.id = :id ")
    void queryUpdateVendaCompraById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE NotaFiscalVenda n WHERE n.vendaCompra.id = :id ")
    void queryDeleteNotaFiscalVendaById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE StatusRastreio s WHERE s.vendaCompra.id = :id ")
    void queryDeleteStatusRastreioById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE VendaCompra v WHERE v.id = :id ")
    void queryDeleteVendaCompraById(@Param("id") Long id);

    @Query(nativeQuery = true, value = """
                    SELECT pf.id                     AS idCliente,
                           venda.id                  AS idVenda,
                           pf.nome                   AS nomeCliente,
                           pf.email                  AS emailCliente,
                           array_agg(t.numero)       AS telefoneCliente,
                           ivc.quantidade            AS quantidadeProduto,
                           prod.id                   AS idProduto,
                           prod.nome                 AS produtoNome,
                           venda.status_venda_compra AS statusVenda
                    FROM venda_compra venda
                            INNER JOIN item_venda_compra ivc ON venda.id = ivc.venda_compra_id
                            INNER JOIN produto prod ON prod.id = ivc.produto_id
                            INNER JOIN pessoa pf ON pf.id = venda.pessoa_id
                            INNER JOIN telefone t ON pf.id = t.pessoa_id
                            WHERE venda.status_venda_compra = 'CANCELADA'
                            AND data_venda BETWEEN :dataInicial AND :dataFinal
                            AND venda.empresa_id = :idEmpresa
                    GROUP BY pf.id, venda.id, pf.nome, pf.email, ivc.quantidade, prod.id, prod.nome, venda.status_venda_compra
            """)
    List<ReportVendaCanceladaProjection> relatorioVendaCancelada(
            @Param("idEmpresa") Long idEmpresa,
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal);
}


