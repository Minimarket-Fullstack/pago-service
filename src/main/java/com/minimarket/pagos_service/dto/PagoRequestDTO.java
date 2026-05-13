package com.minimarket.pagos_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

//monto/metodo/estado
//    private LocalDateTime fechaPago; // para que le voy a pedir la fecha en el json si se crea solo?
    private Double monto;

    private String metodo;

    private String estado;
}
