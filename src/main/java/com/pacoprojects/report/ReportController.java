package com.pacoprojects.report;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService serviceReport;

    @PostMapping
    public ResponseEntity<List<ReportNotaFiscalProjection>> relatorioProdutoPorNotaFiscal(@Valid @RequestBody ReportNotaFiscalDto notaFiscalDto) {
       return ResponseEntity.ok( serviceReport.relatorioProdutoPorNotaFiscal(notaFiscalDto));
    }
}
