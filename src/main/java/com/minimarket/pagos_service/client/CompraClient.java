package com.minimarket.pagos_service.client;

import com.minimarket.pagos_service.dto.CompraResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="compra-service", url= "${compra.service.url}")
public interface CompraClient {
    @GetMapping("api/v1/compras/{id}")
    CompraResponseDTO obtenerPorId(@PathVariable Long id);
}
