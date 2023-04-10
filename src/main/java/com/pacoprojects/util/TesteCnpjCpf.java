package com.pacoprojects.util;

public class TesteCnpjCpf {
    public static void main(String[] args) {
        boolean cnpj = ValidadorCnpj.isCNPJ("33.460.650/0001-79");
        boolean cpf = ValidadorCpf.isCPF("107.775.376-48");

        System.out.println("Cnpj válido: " + cnpj);
        System.out.println("Cpf válido: " + cpf);

    }
}
