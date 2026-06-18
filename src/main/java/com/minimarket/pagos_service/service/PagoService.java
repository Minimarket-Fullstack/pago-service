package com.minimarket.pagos_service.service;

import com.minimarket.pagos_service.client.CompraClient;
import com.minimarket.pagos_service.client.VentaClient;
import com.minimarket.pagos_service.dto.CompraResponseDTO;
import com.minimarket.pagos_service.dto.PagoRequestDTO;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.exception.CompraNotFoundException;
import com.minimarket.pagos_service.exception.PagoNotFoundException;
import com.minimarket.pagos_service.exception.VentaNotFoundException;
import com.minimarket.pagos_service.model.EstadoPago;
import com.minimarket.pagos_service.model.MetodoPago;
import com.minimarket.pagos_service.model.Pago;
import com.minimarket.pagos_service.repository.PagoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CompraClient compraClient;
    private final VentaClient ventaClient;

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

    private void validarVentaId(Long ventaid){
        try{
            ventaClient.obtenerPorId(ventaid);
            log.info("LA VENTA CON EL ID {} HA SIDO VALIDADA CORRECTAMENTE", ventaid);
        } catch (FeignException.NotFound e){
            throw new VentaNotFoundException(ventaid);
        } catch (Exception e){
            throw new RuntimeException("NO SE PUDO CONECTAR CON EL VENTA-SERVICE: " + e.getMessage());
        }
    }

    private CompraResponseDTO validarCompraId(Long compraId){
        try{
            CompraResponseDTO compra = compraClient.obtenerPorId(compraId);
            log.info("LA COMPRA CON EL ID {} HA SIDO VALIDADA CORRECTAMENTE", compraId);
            return compra;
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

        validarVentaId(dto.getVentaId());

        EstadoPago estado = switch (dto.getMetodo().toUpperCase()){
            case "EFECTIVO", "TARJETA", "TRANSFERENCIA" -> EstadoPago.PAGADO;
            default -> throw new IllegalArgumentException("MÉTODO DE PAGO NO VÁLIDO: " +dto.getMetodo());
        };

        String referencia;
        do{
            referencia = "PED-" + ((int) (Math.random() * 900000) + 100000);
        } while(pagoRepository.existsByReferencia(referencia));

        CompraResponseDTO compra = validarCompraId(dto.getCompraId());

        if(!compra.getEstado().equals("PENDIENTE")){
            throw new IllegalArgumentException("LA COMPRA NO ESTÁ EN ESTADO PENDIENTE");
        }

        Pago pago = new Pago(null, dto.getCompraId(),dto.getVentaId(), LocalDateTime.now(),dto.getMonto(), MetodoPago.valueOf(dto.getMetodo().toUpperCase()), estado, referencia,true);
        log.info("GUARDANDO PAGO CON DATOS: compraId={}, ventaId={} monto={}, método={}",dto.getCompraId(),dto.getMonto(),dto.getVentaId(),dto.getMetodo());
        return mapToDto(pagoRepository.save(pago));
    }

    public PagoResponseDTO obtenerPorId(Long id){
        return pagoRepository.findByIdAndActivoTrue(id).map(this::mapToDto).orElseThrow(() -> new PagoNotFoundException(id));
    }


    public PagoResponseDTO actualizarPago(Long id, PagoRequestDTO dto) {
        Pago pago = pagoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new PagoNotFoundException(id));

        validarVentaId(dto.getVentaId());
        CompraResponseDTO compra = validarCompraId(dto.getCompraId());

        if (!compra.getEstado().equals("PENDIENTE")) {
            throw new IllegalArgumentException("LA COMPRA NO ESTÁ EN ESTADO PENDIENTE");
        }

        EstadoPago estado = switch (dto.getMetodo().toUpperCase()) {
            case "EFECTIVO", "TARJETA", "TRANSFERENCIA" -> EstadoPago.PAGADO;
            default -> throw new IllegalArgumentException("MÉTODO DE PAGO NO VÁLIDO: " + dto.getMetodo());
        };

        pago.setCompraId(dto.getCompraId());
        pago.setVentaId(dto.getVentaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(MetodoPago.valueOf(dto.getMetodo().toUpperCase()));
        pago.setEstado(estado);

        return mapToDto(pagoRepository.save(pago));
    }

    //eliminar
    public void eliminarPago(Long id) {
        Pago pago = pagoRepository.findByIdAndActivoTrue(id).orElseThrow(() -> new PagoNotFoundException(id));
        pago.setActivo(false);
        pagoRepository.save(pago);
        log.info("PAGO ELIMINADO EXITOSAMENTE");
    }




}
