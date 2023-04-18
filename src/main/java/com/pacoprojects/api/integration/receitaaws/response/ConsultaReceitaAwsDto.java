

package com.pacoprojects.api.integration.receitaaws.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public record ConsultaReceitaAwsDto(

        String abertura,
        String situacao,
        String tipo,
        String nome,
        String fantasia,
        String porte,
        String natureza_juridica,
        List<AtividadeDto> atividade_principal,
        List<AtividadeDto> atividades_secundarias,
        List<Qsa> qsa,
        String logradouro,
        String numero,
        String complemento,
        String municipio,
        String bairro,
        String uf,
        String cep,
        String email,
        String telefone,
        String data_situacao,
        String cnpj,
        String ultima_atualizacao,
        String status,
        String efr,
        String motivo_situacao,
        String situacao_especial,
        String data_situacao_especial,
        String capital_social,
        @JsonIgnore
        Extra extra,
        Billing billing

) {
}

record AtividadeDto(String code, String text) { }
record Qsa(String nome, String qual, String pais_origem, String nome_rep_legal, String qual_rep_legal) { }
record Extra() { }
record Billing(boolean free, boolean database) { }
