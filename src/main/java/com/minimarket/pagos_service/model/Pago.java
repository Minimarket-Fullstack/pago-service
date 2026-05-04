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
    private MetodoPago metodo_pago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    private String referencia;

    private boolean activo = true;
}
