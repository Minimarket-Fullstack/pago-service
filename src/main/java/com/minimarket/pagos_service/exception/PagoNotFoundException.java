package com.minimarket.pagos_service.exception;

public class PagoNotFoundException extends RuntimeException{
    public PagoNotFoundException(Long id){
        super("PAGO CON EL ID " + id + " NO ENCONTRADO");
    }

}
