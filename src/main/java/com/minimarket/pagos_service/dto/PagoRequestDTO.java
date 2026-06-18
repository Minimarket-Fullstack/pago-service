package com.minimarket.pagos_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para registrar un pago")
public class PagoRequestDTO {

    @Schema(description = "ID de la compra asociada", example = "1")
    @NotNull(message = "EL ID DE LA COMPRA ES OBLIGATORIO")
    private Long compraId;

    @Schema(description = "ID de la venta asociada", example = "1")
    @NotNull(message="EL ID DE LA VENTA ES OBLIGATORIO")
    private Long ventaId;

    @Schema(description = "Monto del pago", example = "15990")
    @NotNull(message="EL MONTO ES OBLIGATORIO")
    @Positive(message="EL MONTO DEBE SER MAYOR A CERO")
    private Double monto;

    @Schema(
            description = "Método de pago",
            example = "TARJETA",
            allowableValues = {"EFECTIVO", "TARJETA", "TRANSFERENCIA"}
    )
    @NotNull(message = "EL MÉTODO DE PAGO ES OBLIGATORIO")
    private String metodo;
}