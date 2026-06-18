package com.minimarket.pagos_service.controller;

import com.minimarket.pagos_service.assembler.PagoModelAssembler;
import com.minimarket.pagos_service.dto.PagoRequestDTO;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos V2 - HATEOAS", description = "CRUD de pagos con enlaces HATEOAS")
public class PagoControllerV2 {

    private final PagoService pagoService;
    private final PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar pagos con HATEOAS")
    public CollectionModel<EntityModel<PagoResponseDTO>> listarPagos() {
        var pagos = pagoService.obtenerTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).listarPagos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar pago por ID con HATEOAS")
    public EntityModel<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return assembler.toModel(pagoService.obtenerPorId(id));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear pago con HATEOAS")
    public ResponseEntity<EntityModel<PagoResponseDTO>> crearPago(@Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO pago = pagoService.guardarPago(dto);
        return ResponseEntity.status(201).body(assembler.toModel(pago));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar pago con HATEOAS")
    public EntityModel<PagoResponseDTO> actualizarPago(@PathVariable Long id,
                                                       @Valid @RequestBody PagoRequestDTO dto) {
        return assembler.toModel(pagoService.actualizarPago(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar pago")
    public ResponseEntity<Map<String, String>> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.ok(Map.of("mensaje", "Pago eliminado correctamente"));
    }
}
