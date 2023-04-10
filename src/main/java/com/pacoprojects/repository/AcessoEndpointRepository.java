package com.pacoprojects.repository;

import com.pacoprojects.model.AcessoEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoEndpointRepository extends JpaRepository<AcessoEndpoint, Long> {

    boolean existsByNome(String nome);

    @Modifying
    @Query(value = "update AcessoEndpoint a set a.quantidade = a.quantidade + 1 where a.nome = ?1")
    void updateAcessoEndpointByNome(String EndPointName);
}
