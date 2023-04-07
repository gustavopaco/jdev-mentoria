package com.pacoprojects.model;

import com.pacoprojects.enums.TipoEndereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "endereco")
@Entity
public class Endereco {

    @Id
    @SequenceGenerator(name = "sequence_endereco", sequenceName = "sequence_endereco", allocationSize = 1)
    @GeneratedValue(generator = "sequence_endereco", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Rua obrigatório.")
    @Column(name = "rua", nullable = false)
    private String rua;

    @NotBlank(message = "Cep obrigatório.")
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotBlank(message = "Número obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotBlank(message = "Bairro obrigatório.")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "Cidade obrigatório.")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotBlank(message = "Estado obrigatório.")
    @Column(name = "estado", nullable = false)
    private String estado;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Tipo de endereço obrigatório.")
    @Column(name = "tipo_endereco", nullable = false)
    private TipoEndereco tipoEndereco;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "pessoa_id_fk", value = ConstraintMode.CONSTRAINT))
    private Pessoa pessoa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Endereco endereco = (Endereco) o;
        return getId() != null && Objects.equals(getId(), endereco.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
