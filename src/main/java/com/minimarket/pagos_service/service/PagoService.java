package com.minimarket.pagos_service.service;


import com.minimarket.pagos_service.dto.PagoRequestDTO;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.model.EstadoPago;
import com.minimarket.pagos_service.model.MetodoPago;
import com.minimarket.pagos_service.model.Pago;
import com.minimarket.pagos_service.repository.PagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    // id, compraid, ventaid, , fechaPago, metodoPago, monto, estado, referencia
    private  PagoResponseDTO mapToDto(Pago pago){
        return new PagoResponseDTO(
                pago.getId(),
                pago.getCompraId(),
                pago.getVentaId(),
                pago.getFechaPago(),
                pago.getMetodoPago().name(),
                pago.getMonto(),
                pago.getEstado().name(),
                pago.getReferencia());

    }

    public List<PagoResponseDTO> obtenerTodos(){
        return pagoRepository.findByActivoTrue().stream().map(this::mapToDto).collect(Collectors.toList());

    }

    public PagoResponseDTO guardarPago(PagoRequestDTO dto){
        //pago: id, compraid, ventaid, fechaPago, monto, metodo,estado, referencia, activo
        Pago pago = new Pago(1L, 1L, 1L, LocalDateTime.now(), dto.getMonto(), MetodoPago.valueOf(dto.getMetodo()), EstadoPago.valueOf(dto.getEstado()), "kasks", true);
        return mapToDto(pagoRepository.save(pago));
    }



}
