package com.minimarket.pagos_service.exception;

public class VentaNotFoundException extends RuntimeException {
    public VentaNotFoundException(Long ventaId) {
        super("VENTA CON EL ID " + ventaId + " NO EXISTE EN EL VENTA-SERVICE");
    }
}
