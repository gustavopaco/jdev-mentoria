package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boleto_juno")
@Entity
public class BoletoJuno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_boleto_juno")
    @SequenceGenerator(name = "sequence_boleto_juno", sequenceName = "sequence_boleto_juno", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    // codigo que faz referencia ao boleto
    @Column(name = "code")
    private Long code;

    // Imprime o boleto completo com todas as parcelas
    @Column(name = "link")
    private String link;

    // Mostra uma telinha de checkout da juno com os boleto, pix e cartao pagos ou vencidos
    @Column(name = "checkout_url")
    private String checkoutUrl;

    @Column(name = "quitado", nullable = false)
    private boolean quitado = false;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;

    @Column(name = "valor",columnDefinition = "NUMERIC")
    private BigDecimal valor = BigDecimal.ZERO;

    // Se for parcelado
    @Column(name = "recorrencia")
    private Integer recorrencia = 0;

    // Id Controle do boleto para poder cancelar pela Api
    @Column(name = "id_chr_boleto")
    private String idChrBoleto;

    // ResponseCriarCobrancaLinkDto da parcela do boleto
    @Column(name = "installment_link")
    private String installmentLink;

    @Column(name = "id_pix")
    private String idPix;

    @Column(name = "payload_in_base_64", columnDefinition = "TEXT")
    private String payloadInBase64;

    @Column(name = "image_in_base_64", columnDefinition = "TEXT")
    private String imageInBase64;

    @Column(name = "charge_i_cartao")
    private String chargeICartao;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(targetEntity = VendaCompra.class)
    @JoinColumn(name = "venda_compra_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "venda_compra_id_fk", value = ConstraintMode.CONSTRAINT))
    private VendaCompra vendaCompra;

    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(
            name = "empresa_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "empresa_id_fk", value = ConstraintMode.CONSTRAINT))
    private PessoaJuridica empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BoletoJuno that = (BoletoJuno) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
