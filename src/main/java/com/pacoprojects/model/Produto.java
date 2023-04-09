package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "Descrição do produto obrigatório.")
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Peso do produto obrigatório.")
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull(message = "Largura do produto obrigatório.")
    @Column(name = "largura", nullable = false)
    private Double largura;

    @NotNull(message = "Altura do produto obrigatório.")
    @Column(name = "altura", nullable = false)
    private Double altura;

    @NotNull(message = "Profundidade do produto obrigatório.")
    @Column(name = "profundidade", nullable = false)
    private Double profundidade;

    @NotNull(message = "Valor de venda obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "valor_venda", nullable = false)
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @NotNull(message = "Quantidade de estoque do produto obrigatório.")
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

    // TODO NotaItemProduto Associonar

    @ManyToOne(targetEntity = MarcaProduto.class)
    @JoinColumn(
            name = "marca_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "marca_id_fk", value = ConstraintMode.CONSTRAINT))
    private MarcaProduto marcaProduto;

    @ManyToMany(targetEntity = Categoria.class, mappedBy = "produtos")
    @ToString.Exclude
    private Set<Categoria> categorias = new LinkedHashSet<>();

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa empresa;

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
