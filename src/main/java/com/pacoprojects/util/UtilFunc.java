package com.pacoprojects.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilFunc {

    public static String formatCpfCpnj(String cpfCnpj) {
        return cpfCnpj.replaceAll("\\.", "")
                .replaceAll("-", "")
                .replaceAll("/", "");
    }

    public static String formatCep(String cep) {
        return cep.replaceAll("-", "");
    }

    public static BigDecimal formatValorTotalParcelas(BigDecimal valorTotal, Integer parcelas) {
        if (parcelas == 1) {
            return valorTotal;
        } else {
            return valorTotal.divide(new BigDecimal(parcelas), 2, RoundingMode.HALF_DOWN);
        }
    }
}
