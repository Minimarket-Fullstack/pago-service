package com.minimarket.pagos_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
//id, vcompraId,ventaid,fecha,monto,metodopago,estado,,referneica,activo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long compraId;

    private Long ventaId;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    // esto lo dejaría como numero de pedido
    @Column(unique = true)  //para q no salgan duplicados
    private String referencia; // me gustaría q esto se generará solo

    private boolean activo = true; // este es el borrado lógico q tengo en los otros microservicios fomes.
}
