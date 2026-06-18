package com.minimarket.pagos_service.exception;

public class CompraNotFoundException extends RuntimeException {
    public CompraNotFoundException(Long id) {
        super("LA COMPRA CON EL ID: " + id + " NO EXISTE EN COMPRA-SERVICE");
    }
}
