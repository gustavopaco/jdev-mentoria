package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.ItemVendaCompraSelected;
import com.pacoprojects.model.ItemVendaCompra;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ItemVendaCompraRepository extends JpaRepository<ItemVendaCompra, Long> {

    @Query(value = "select i from  ItemVendaCompra i where i.produto.id = ?1 and i.vendaCompra.enabled = true")
    List<ItemVendaCompraSelected> queryFindAllVendaCompraByProduto_Id(Long idProduto);
    List<ItemVendaCompraSelected> findAllByProduto_IdAndVendaCompra_Enabled(Long idProduto, boolean enabled);



    @Query(value = "select i from ItemVendaCompra i where i.produto.nome ilike %?1%  ")
    List<ItemVendaCompraSelected> queryProdutoByNomeAndVendaCompraAssociated(String nomeProduto);

    List<ItemVendaCompraSelected> findByProdutoNomeContainingIgnoreCaseAndVendaCompraEnabled(String nomeProduto, boolean enabled);



    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.pessoa.nome ilike %:nomeCliente% and i.vendaCompra.enabled = true")
    List<ItemVendaCompraSelected> queryPessoaByNomeAndVendaCompraEnabled(@Param("nomeCliente") String nomeCliente);

    List<ItemVendaCompraSelected> findAllByVendaCompra_Pessoa_NomeContainsIgnoreCaseAndVendaCompra_Enabled(String nomeCliente, boolean enabled);


    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.enderecoCobranca.rua ilike %:endCobranca% and i.vendaCompra.enabled = true")
    List<ItemVendaCompraSelected> queryEnderecoCobrancaByRuaAndVendaCompraEnabled(@Param("endCobranca") String endCobranca);

    List<ItemVendaCompraSelected> findAllByVendaCompra_EnderecoCobranca_RuaContainsIgnoreCaseAndVendaCompra_Enabled(String endCobranca, boolean enabled);



    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.enderecoEntrega.rua ilike %:endEntrega% and i.vendaCompra.enabled = true")
    List<ItemVendaCompraSelected> queryEnderecoEntregaByRuaAndVendaCompraEnabled(@Param("endEntrega") String endEntrega);

    List<ItemVendaCompraSelected> findAllByVendaCompra_EnderecoEntrega_RuaContainsIgnoreCaseAndVendaCompra_Enabled(String endEntrega, boolean enabled);
}
