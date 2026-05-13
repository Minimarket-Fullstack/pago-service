package com.minimarket.pagos_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagoResponseDTO {



    // id, compraid, ventaid, referencia, fechaPago, metodoPago, monto, estado
    private Long id; // esto no contaría como numero de pedido?

    private Long compraId;

    private Long ventaId;


        // pero si retorno la fecha po
    private LocalDateTime fechaPago;

    private String metodoPago;

    private Double monto;

    //Al final le muestro el estado del pago
    private String estado; // los enums se muestran como string

    private String referencia; //Aquí pondría el número del pedido.




}
