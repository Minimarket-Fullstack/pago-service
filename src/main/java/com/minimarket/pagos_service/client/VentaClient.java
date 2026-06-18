package com.minimarket.pagos_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ventas-service", url = "${ventas.service.url}")
public interface VentaClient {
    @GetMapping("api/v1/ventas/{id}")
    String obtenerPorId(@PathVariable Long id);
}
