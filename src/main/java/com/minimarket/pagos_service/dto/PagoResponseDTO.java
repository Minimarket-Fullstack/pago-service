package com.minimarket.pagos_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Información completa de un pago")
public class PagoResponseDTO {

    @Schema(description = "ID del pago", example = "1")
    private Long id;

    @Schema(description = "ID de la compra asociada", example = "5")
    private Long compraId;

    @Schema(description = "ID de la venta asociada", example = "10")
    private Long ventaId;

    @Schema(description = "Fecha y hora del pago")
    private LocalDateTime fechaPago;

    @Schema(description = "Método de pago utilizado", example = "TARJETA")
    private String metodoPago;

    @Schema(description = "Monto pagado", example = "15990")
    private Double monto;

    @Schema(description = "Estado actual del pago", example = "PAGADO")
    private String estado;

    @Schema(description = "Número de referencia del pago", example = "PED-123456")
    private String referencia;
}
