package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
@Entity
public class Produto {

    @Id
    @SequenceGenerator(name = "sequence_produto", sequenceName = "sequence_produto", allocationSize = 1)
    @GeneratedValue(generator = "sequence_produto", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Tipo da unidade do produto obrigatório.")
    @Column(name = "tipo_unidade", nullable = false)
    private String tipoUnidade;

    @NotBlank(message = "Nome do produto obrigatório.")
    @Size(min = 10, message = "Nome do produto deve ter no mínimo de 10 caracteres.")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "Descrição do produto obrigatório.")
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Peso do produto obrigatório.")
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull(message = "Largura em Centímetros do produto obrigatório.")
    @Column(name = "largura", nullable = false)
    private Integer largura;

    @NotNull(message = "Altura em Centímetros do produto obrigatório.")
    @Column(name = "altura", nullable = false)
    private Integer altura;

    @NotNull(message = "Profundidade em Centímetros do produto obrigatório.")
    @Column(name = "profundidade", nullable = false)
    private Integer profundidade;

    @NotNull(message = "Valor de venda obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_venda", nullable = false)
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @NotNull(message = "Quantidade de estoque do produto obrigatório.")
    @Min(value = 1, message = "Quantidade de estoque deve ser no mínimo 1.")
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque = 0;

    @Column(name = "quantidade_alerta_estoque")
    private Integer quantidadeAlertaEstoque = 0;

    @Column(name = "link_youtube")
    private String linkYoutube;

    @Column(name = "quantidade_click")
    private Integer quantidadeClick = 0;

    @Column(name = "alerta_estoque_enabled")
    private Boolean alertaEstoqueEnabled = Boolean.FALSE;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = Boolean.TRUE;

    @NotNull(message = "Marca deve ser informado.")
    @ManyToOne(targetEntity = MarcaProduto.class)
    @JoinColumn(
            name = "marca_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "marca_id_fk", value = ConstraintMode.CONSTRAINT))
    private MarcaProduto marcaProduto;

    @NotEmpty(message = "Categoria de Produto deve ser informada.")
    @ManyToMany(targetEntity = Categoria.class, mappedBy = "produtos")
    @ToString.Exclude
    private Set<Categoria> categorias = new LinkedHashSet<>();

    @NotNull(message = "Empresa deve ser informado.")
    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private PessoaJuridica empresa;

    @Size(min = 3, max = 6, message = "Produto de ter pelo menos 3 imagem e no máximo 6 associado a ele.")
    @ToString.Exclude
    @OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<ImagemProduto> imagemProdutos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Produto produto = (Produto) o;
        return getId() != null && Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
