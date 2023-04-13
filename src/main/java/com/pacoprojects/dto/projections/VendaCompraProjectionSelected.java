package com.pacoprojects.dto.projections;

import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.enums.TipoPessoa;
import com.pacoprojects.model.ItemVendaCompra;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
 */
@Projection(name = "vendaCompraProjectionSelected", types = ItemVendaCompra.class)
public interface VendaCompraProjectionSelected {
    Long getId();

    BigDecimal getValorTotal();

    BigDecimal getValorDesconto();

    BigDecimal getValorFrete();

    Integer getDiasParaEntrega();

    LocalDate getDataVenda();

    LocalDate getDataEntrega();

    PessoaFisicaInfo getPessoa();

    EnderecoInfo getEnderecoEntrega();

    FormaPagamentoInfo getFormaPagamento();

    PessoaJuridicaInfo getEmpresa();

    Set<ItemVendaCompraInfo> getItemVendaCompras();

    /**
     * A Projection for the {@link com.pacoprojects.model.PessoaFisica} entity
     */
    interface PessoaFisicaInfo {
        Long getId();

        String getNome();

        String getEmail();

        TipoPessoa getTipoPessoa();

        String getCpf();

        LocalDate getDataNascimento();

        Set<TelefoneInfo> getTelefones();

        Set<EnderecoInfo> getEnderecos();

        /**
         * A Projection for the {@link com.pacoprojects.model.Telefone} entity
         */
        interface TelefoneInfo {
            Long getId();

            String getNumero();
        }

        /**
         * A Projection for the {@link com.pacoprojects.model.Endereco} entity
         */
        interface EnderecoInfo {
            Long getId();

            String getRua();

            String getCep();

            String getNumero();

            String getComplemento();

            String getBairro();

            String getCidade();

            String getEstado();

            TipoEndereco getTipoEndereco();
        }
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.Endereco} entity
     */
    interface EnderecoInfo {
        Long getId();

        String getRua();

        String getCep();

        String getNumero();

        String getComplemento();

        String getBairro();

        String getCidade();

        String getEstado();

        TipoEndereco getTipoEndereco();
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.FormaPagamento} entity
     */
    interface FormaPagamentoInfo {
        Long getId();

        String getDescricao();
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.PessoaJuridica} entity
     */
    interface PessoaJuridicaInfo {
        Long getId();

        String getNome();

        String getCnpj();

        String getInscricaoEstadual();

        String getNomeFantasia();

        String getRazaoSocial();

        String getCategoria();
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.ItemVendaCompra} entity
     */
    interface ItemVendaCompraInfo {
        Long getId();

        Double getQuantidade();

        ProdutoInfo getProduto();

        /**
         * A Projection for the {@link com.pacoprojects.model.Produto} entity
         */
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
        }
    }
}
