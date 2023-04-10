package com.pacoprojects.service;

import com.pacoprojects.model.AcessoEndpoint;
import com.pacoprojects.repository.AcessoEndpointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EndPointService {

    private final AcessoEndpointRepository repository;

    @Transactional
    public void updateEndPoint(String endPointName) {

        if (repository.existsByNome(endPointName)) {
            repository.updateAcessoEndpointByNome(endPointName);
        } else {
            repository.save(AcessoEndpoint
                    .builder()
                    .nome(endPointName)
                    .quantidade(new BigDecimal("1"))
                    .build());
        }
    }
}
