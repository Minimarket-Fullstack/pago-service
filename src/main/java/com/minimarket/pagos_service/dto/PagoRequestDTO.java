package com.minimarket.pagos_service.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

//monto/metodo/estado
//    private LocalDateTime fechaPago; // para que le voy a pedir la fecha en el json si se crea solo?
    @NotNull(message = "EL ID DE LA COMPRA ES OBLIGATORIO")
    private Long compraId;

    @NotNull(message="EL ID DE LA VENTA ES OBLIGATORIO")
    private Long ventaId;

    @NotNull(message="EL MONTO ES OBLIGATORIO")
    @Positive(message="EL MONTO DEBE SER MAYOR A CERO")
    private Double monto;

    @NotNull(message = "EL MÉTODO DE PAGO ES OBLIGATORIO")
    private String metodo;

    private String referencia;

    // El estado siemper q guardo un pedido y se paga??? el estadodlo dejo como pendiente siempre?
//    private String estado;
}
