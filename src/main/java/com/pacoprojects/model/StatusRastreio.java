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
@Table(name = "status_rastreio")
@Entity
public class StatusRastreio {

    @Id
    @SequenceGenerator(name = "sequence_status_rastreio", sequenceName = "sequence_status_rastreio", allocationSize = 1)
    @GeneratedValue(generator = "sequence_status_rastreio", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    private String centroDistribuicao;

    private String cidade;

    private String estado;

    private String status;

    @ManyToOne(targetEntity = VendaCompra.class)
    @JoinColumn(
            name = "venda_compra_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_compra_id_fk", value = ConstraintMode.CONSTRAINT))
    private VendaCompra vendaCompra;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private PessoaJuridica empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StatusRastreio that = (StatusRastreio) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
