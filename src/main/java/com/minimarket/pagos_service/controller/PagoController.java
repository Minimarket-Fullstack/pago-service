package com.minimarket.pagos_service.controller;

import com.minimarket.pagos_service.dto.PagoRequestDTO;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Pagos",
        description = "Operaciones relacionadas con la gestión de pagos"
)
@RestController
@RequestMapping("/api/v1/pagos")
@Slf4j
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @Operation(summary = "Listar todos los pagos")
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listar(){
        log.info("GET api/v1/pagos - OBTENER TODOS");
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @Operation(summary = "Buscar pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id){
        log.info("GET api/v1/pagos/{} - OBTENER POR ID",id);
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(summary = "Registrar un nuevo pago")
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crearPago(
            @Valid @RequestBody PagoRequestDTO dto){

        log.info("POST api/v1/pagos CREAR PAGO compraId={}, ventaId={}",
                dto.getCompraId(),
                dto.getVentaId());

        return ResponseEntity.status(201)
                .body(pagoService.guardarPago(dto));
    }

    @Operation(summary = "Eliminar un pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        log.info("DELETE api/v1/pagos/{} - ELIMINAR PAGO POR ID",id);
        pagoService.eliminarPago(id);
        return ResponseEntity.ok(
                Map.of("MENSAJE", "PAGO ELIMINADO CON ÉXITO")
        );
    }
}