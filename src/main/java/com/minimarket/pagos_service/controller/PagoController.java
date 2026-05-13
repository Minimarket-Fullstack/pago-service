package com.minimarket.pagos_service.controller;

import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listar(){
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }
}
