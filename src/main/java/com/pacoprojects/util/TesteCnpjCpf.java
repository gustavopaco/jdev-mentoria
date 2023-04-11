package com.pacoprojects.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteCnpjCpf {
    public static void main(String[] args) {
        boolean cnpj = ValidadorCnpj.isCNPJ("33.460.650/0001-79");
        boolean cpf = ValidadorCpf.isCPF("107.775.376-48");
        String teste = "data:image/png;base64,5ds4f56sd4f5s6df4s6f";

        System.out.println("Cnpj válido: " + cnpj);
        System.out.println("Cpf válido: " + cpf);
        System.out.println(new BCryptPasswordEncoder().encode("12345678"));
        System.out.println(teste.split(",")[1]);
    }
}
