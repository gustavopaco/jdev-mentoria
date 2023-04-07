package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

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

    private String tipoUnidade;

    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    private Double peso;

    private Double largura;

    private Double altura;

    private Double profundidade;

    private BigDecimal valorVenda = BigDecimal.ZERO;

    private Integer quantidadeEstoque = 0;

    private Integer quantidadeAlertaEstoque = 0;

    private String linkYoutube;

    private Integer quantidadeClick = 0;

    private Boolean alertaEstoqueEnabled = Boolean.FALSE;

    private Boolean enabled = Boolean.TRUE;

    // TODO NotaItemProduto Associonar


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
