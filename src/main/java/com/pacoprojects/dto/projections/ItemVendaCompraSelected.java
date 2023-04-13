package com.pacoprojects.dto.projections;

import com.pacoprojects.model.*;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.ItemVendaCompra} entity
 */
@Projection(name = "itemVendaCompraSelected", types = ItemVendaCompra.class)
public interface ItemVendaCompraSelected {
    Double getQuantidade();

    ProdutoInfo getProduto();

    VendaCompraInfo getVendaCompra();

    PessoaJuridicaInfo getEmpresa();

    /**
     * A Projection for the {@link com.pacoprojects.model.Produto} entity
     */
    @Projection(name = "produtoInfo", types = Produto.class)
    interface ProdutoInfo {
        Long getId();

        String getTipoUnidade();

        String getNome();

        String getDescricao();

        Double getPeso();

        Double getLargura();

        Double getAltura();

        Double getProfundidade();

        BigDecimal getValorVenda();

        Integer getQuantidadeEstoque();

        String getLinkYoutube();
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
     */
    @Projection(name = "vendaCompraInfo", types = VendaCompra.class)
    interface VendaCompraInfo {
        Long getId();

        BigDecimal getValorTotal();

        BigDecimal getValorDesconto();

        BigDecimal getValorFrete();

        Integer getDiasParaEntrega();

        LocalDate getDataVenda();

        LocalDate getDataEntrega();

        EnderecoInfo getEnderecoCobranca();

        EnderecoInfo getEnderecoEntrega();

        FormaPagamentoInfo getFormaPagamento();

        /**
         * A Projection for the {@link com.pacoprojects.model.Endereco} entity
         */
        @Projection(name = "enderecoInfo", types = Endereco.class)
        interface EnderecoInfo {
            Long getId();

            String getRua();

            String getCep();

            String getNumero();

            String getComplemento();

            String getBairro();

            String getCidade();

            String getEstado();

            String getTipoEndereco();
        }

        /**
         * A Projection for the {@link com.pacoprojects.model.FormaPagamento} entity
         */
        @Projection(name = "formaPagamentoInfo", types = FormaPagamentoInfo.class)
        interface FormaPagamentoInfo {
            Long getId();

            String getDescricao();
        }
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.PessoaJuridica} entity
     */
    @Projection(name = "pessoaJuridicaInfo", types = PessoaJuridica.class)
    interface PessoaJuridicaInfo {
        Long getId();

        String getNome();

        String getCnpj();

        String getNomeFantasia();

        String getRazaoSocial();

        String getCategoria();
    }
}
