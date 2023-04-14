package com.pacoprojects.controller;

import com.pacoprojects.dto.projections.StatusRastreioProjection;
import com.pacoprojects.service.StatusRastreioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "statusRastreio")
@RequiredArgsConstructor
public class StatusRastreioController {

    private final StatusRastreioService serviceStatusRastreio;

    @GetMapping(path = "{idVenda}")
    public ResponseEntity<List<StatusRastreioProjection>> getAllStatusRastreioByIdVenda(@PathVariable(name = "idVenda") Long idVenda) {
        return ResponseEntity.ok(serviceStatusRastreio.getAllStatusRastreioByIdVenda(idVenda));
    }
}
