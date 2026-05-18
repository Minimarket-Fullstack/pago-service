package com.minimarket.pagos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="compra-service", url= "${compra.service.url}")
public interface CompraClient {
    @GetMapping("api/v1/compras/{id}")
    String obtenerPorId(@PathVariable Long id);
}
