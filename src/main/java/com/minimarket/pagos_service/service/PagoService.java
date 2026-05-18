package com.minimarket.pagos_service.service;


import com.minimarket.pagos_service.client.CompraClient;
import com.minimarket.pagos_service.dto.PagoRequestDTO;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.exception.CompraNotFoundException;
import com.minimarket.pagos_service.exception.PagoNotFoundException;
import com.minimarket.pagos_service.model.EstadoPago;
import com.minimarket.pagos_service.model.MetodoPago;
import com.minimarket.pagos_service.model.Pago;
import com.minimarket.pagos_service.repository.PagoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CompraClient compraClient;

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

    private void validarCompraId(Long compraId){
        try{
            compraClient.obtenerPorId(compraId);
            log.info("LA COMPRA CON EL ID {} HA SIDO VALIDADA CORRECTAMENTE", compraId);
        } catch (FeignException.NotFound e){
            throw new CompraNotFoundException(compraId);
        } catch (Exception e){
            throw new RuntimeException("NO SE PUDO CONECTAR CON EL COMPRA-SERVICE: " + e.getMessage());
        }
    }

    public List<PagoResponseDTO> obtenerTodos(){
        return pagoRepository.findByActivoTrue().stream().map(this::mapToDto).collect(Collectors.toList());
    }
//id, vcompraId,ventaid,fecha,monto,metodopago,estado,,referneica,activo

    //método para guardar
    public PagoResponseDTO guardarPago(PagoRequestDTO dto){

        validarCompraId(dto.getCompraId());

        // como hago para q no quede en pendietne siempre po
        //probando el operador ternario podría haberlo hecho con un swithc igual
          EstadoPago estado =
                dto.getMetodo().equalsIgnoreCase("efectivo")
                        || dto.getMetodo().equalsIgnoreCase("tarjeta")
                        || dto.getMetodo().equalsIgnoreCase("transferencia")
                        ? EstadoPago.PAGADO //if
                        : EstadoPago.PENDIENTE; //else


        String referencia = "PED-" + ((int) (Math.random() * 900000) + 100000);

        //que manera
        Pago pago = new Pago(null, dto.getCompraId(),dto.getVentaId(), LocalDateTime.now(),dto.getMonto(), MetodoPago.valueOf(dto.getMetodo().toUpperCase()), estado, referencia,true);
        log.info("GUARDANDO PAGO CON DATOS: compraId={}, monto={}, método={}",dto.getCompraId(),dto.getMonto(),dto.getMetodo());
        return mapToDto(pagoRepository.save(pago));
    }

    public PagoResponseDTO obtenerPorId(Long id){
        return pagoRepository.findByIdAndActivoTrue(id).map(this::mapToDto).orElseThrow(() -> new PagoNotFoundException(id));
    }

    //eliminar
    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findByIdAndActivoTrue(id).orElseThrow(() -> new PagoNotFoundException(id));
        if(!pago.isActivo()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EL PAGO YA SE ENCUENTRA ELIMINADO");
        }
        pago.setActivo(false);
        pagoRepository.save(pago);
        log.info("PAGO ELIMINADO EXITOSAMENTE");
    }




}
