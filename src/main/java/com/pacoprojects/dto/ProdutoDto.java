package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.Produto} entity
 */
public record ProdutoDto(

        Long id,

        @NotBlank(message = "Tipo da unidade do produto obrigatório.")
        String tipoUnidade,
        @NotBlank(message = "Nome do produto obrigatório.")
        @Size(min = 10, message = "Nome do produto deve ter no mínimo de 10 caracteres.")
        String nome,

        @NotBlank(message = "Descrição do produto obrigatório.")
        String descricao,

        @NotNull(message = "Peso do produto obrigatório.")
        Double peso,

        @NotNull(message = "Largura do produto obrigatório.")
        Double largura,

        @NotNull(message = "Altura do produto obrigatório.")
        Double altura,

        @NotNull(message = "Profundidade do produto obrigatório.")
        Double profundidade,

        @NotNull(message = "Valor de venda obrigatório.")
        BigDecimal valorVenda,

        @NotNull(message = "Quantidade de estoque do produto obrigatório.")
        Integer quantidadeEstoque,

        @NotNull(message = "Marca deve ser informado.")
        MarcaProdutoDtoBasicId marcaProduto,

        @NotEmpty(message = "Categoria de Produto deve ser informada.")
        Set<CategoriaDtoBasicId> categorias,

        @NotNull(message = "Empresa deve ser informado.")
        PessoaJuridicaDtoBasicId empresa,

        Integer quantidadeAlertaEstoque,

        String linkYoutube,

        Integer quantidadeClick,

        Boolean alertaEstoqueEnabled,

        Boolean enabled,

        @Size(min = 3, max = 6, message = "Produto de ter pelo menos 3 imagem e no máximo 6 associado a ele.")
        List<ImagemProdutoDto> imagemProdutos

) implements Serializable {

    @Override
    public Boolean enabled() {
        return Objects.requireNonNullElse(enabled, true);
    }

    @Override
    public Boolean alertaEstoqueEnabled() {
        return Objects.requireNonNullElse(alertaEstoqueEnabled, true);
    }
}
