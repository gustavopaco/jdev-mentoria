package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "imagem_produto")
@Entity
public class ImagemProduto {

    @Id
    @SequenceGenerator(name = "sequence_imagem_produto", sequenceName = "sequence_imagem_produto", allocationSize = 1)
    @GeneratedValue(generator = "sequence_imagem_produto", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String imagemOriginal;

    @Column(columnDefinition = "TEXT")
    private String imegemMiniatura;

    @ManyToOne(targetEntity = Produto.class)
    @JoinColumn(
            name = "produto_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "produto_id_fk", value = ConstraintMode.CONSTRAINT))
    private Produto produto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ImagemProduto that = (ImagemProduto) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
