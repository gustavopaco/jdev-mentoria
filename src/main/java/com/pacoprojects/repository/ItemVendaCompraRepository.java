package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.ItemVendaCompraSelected;
import com.pacoprojects.model.ItemVendaCompra;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ItemVendaCompraRepository extends JpaRepository<ItemVendaCompra, Long> {


    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.pessoa.id =:idCliente and i.vendaCompra.enabled =:enabled ")
    List<ItemVendaCompraSelected> queryPessoaByIdAndVendaCompraEnabled(@Param("idCliente") Long id, @Param("enabled") boolean enabled);
    List<ItemVendaCompraSelected> findAllByVendaCompra_Pessoa_IdAndVendaCompra_Enabled(Long idCliente, boolean enabled);

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



    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.dataVenda between :dataInicial and :dataFinal and i.vendaCompra.enabled ")
    List<ItemVendaCompraSelected> findAllByVendaCompraDataVendaBetween_DataInicial_And_DataFinal(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);

    List<ItemVendaCompraSelected> findAllByVendaCompra_DataVendaBetweenAndVendaCompra_Enabled(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal, boolean enabled);




    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.pessoa.cpf =:cpf and i.vendaCompra.enabled = true")
    List<ItemVendaCompraSelected> queryAllVendaCompraByCpf(@Param("cpf") String cpf);

    List<ItemVendaCompraSelected> findAllByVendaCompra_Pessoa_CpfAndVendaCompra_Enabled(String cpf, boolean enabled);


    @Query(value = "select i from ItemVendaCompra i where i.vendaCompra.pessoa.nome ilike %:nome% and i.vendaCompra.pessoa.cpf =:cpf and i.vendaCompra.enabled = true")
    List<ItemVendaCompraSelected> queryAllVendaCompraByCpfAndNome(@Param("nome") String nome, @Param("cpf") String cpf);

    List<ItemVendaCompraSelected> findAllByVendaCompra_Pessoa_NomeContainsIgnoreCaseAndVendaCompra_Pessoa_CpfAndVendaCompra_Enabled(String nome, String cpf, boolean enabled);
}
