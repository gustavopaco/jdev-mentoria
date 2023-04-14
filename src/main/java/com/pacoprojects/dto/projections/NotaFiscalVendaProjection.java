package com.pacoprojects.dto.projections;

import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.enums.TipoPessoa;
import com.pacoprojects.model.NotaFiscalVenda;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * A Projection for the {@link com.pacoprojects.model.NotaFiscalVenda} entity
 */
@Projection(name = "notaFiscalVendaProjection", types = NotaFiscalVenda.class)
public interface NotaFiscalVendaProjection {
    Long getId();

    String getNumero();

    String getSerie();

    String getTipo();

    String getXml();

    String getPdf();

    VendaCompraInfo getVendaCompra();

    PessoaJuridicaInfo getEmpresa();

    /**
     * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
     */
    interface VendaCompraInfo {
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

            /**
             * A Projection for the {@link com.pacoprojects.model.Telefone} entity
             */
            interface TelefoneInfo {
                Long getId();

                String getNumero();
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

                MarcaProdutoInfo getMarcaProduto();

                Set<CategoriaInfo> getCategorias();

                /**
                 * A Projection for the {@link com.pacoprojects.model.MarcaProduto} entity
                 */
                interface MarcaProdutoInfo {
                    Long getId();

                    String getNome();
                }

                /**
                 * A Projection for the {@link com.pacoprojects.model.Categoria} entity
                 */
                interface CategoriaInfo {
                    Long getId();

                    String getNome();
                }
            }
        }
    }

    /**
     * A Projection for the {@link com.pacoprojects.model.PessoaJuridica} entity
     */
    interface PessoaJuridicaInfo {
        Long getId();

        String getNome();

        String getEmail();

        String getCnpj();

        String getInscricaoEstadual();

        String getInscricaoMunicipal();

        String getNomeFantasia();

        String getRazaoSocial();

        String getCategoria();

        Set<EnderecoInfo> getEnderecos();

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
}
