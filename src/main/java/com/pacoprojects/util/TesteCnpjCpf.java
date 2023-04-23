package com.pacoprojects.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

public class TesteCnpjCpf {
    public static void main(String[] args) {
        boolean cnpj = ValidadorCnpj.isCNPJ("33.460.650/0001-79");
        boolean cpf = ValidadorCpf.isCPF("107.775.376-48");
        String teste = "data:image/png;base64,5ds4f56sd4f5s6df4s6f";

        System.out.println("Cnpj válido: " + cnpj);
        System.out.println("Cpf válido: " + cpf);
        System.out.println(new BCryptPasswordEncoder().encode("12345678"));
        System.out.println(teste.split(",")[1]);
        System.out.println(LocalDate.now().plusDays(7).toString());
        System.out.println(LocalDate.parse("1989-09-24").atStartOfDay());
        System.out.println(List.of("ANGULAR", "JAVA", "REACT").toString());
    }
}
