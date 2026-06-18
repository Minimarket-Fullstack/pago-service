package com.minimarket.pagos_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información resumida de una compra")
public class CompraResponseDTO {

    @Schema(description = "ID de la compra", example = "1")
    private Long id;

    @Schema(description = "Monto total de la compra", example = "25990")
    private Double total;

    @Schema(description = "Estado de la compra", example = "PENDIENTE")
    private String estado;
}