package com.minimarket.pagos_service.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotNull(message = "EL ID DEL PAGO ES OBLIGATORIO")
    @Positive(message= "EL ID DEBE SER MAYOR A CERO")
    private Long id;
//monto/metodo/estado
//    private LocalDateTime fechaPago; // para que le voy a pedir la fecha en el json si se crea solo?
    private Double monto;

    private String metodo;

    private String estado;
}
