package com.minimarket.pagos_service.service;


import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.model.Pago;
import com.minimarket.pagos_service.repository.PagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoResponseDTO mapToDto(Pago pago){

    }

    public List<PagoResponseDTO> obtenerTodos(){
        return pagoRepository.findByActivoTrue().stream().map(this::mapToDto).collect(Collectors.toList());

    }



}
