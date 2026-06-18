import com.minimarket.pagos_service.client.CompraClient;
import com.minimarket.pagos_service.client.VentaClient;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import com.minimarket.pagos_service.model.EstadoPago;
import com.minimarket.pagos_service.model.MetodoPago;
import com.minimarket.pagos_service.model.Pago;
import com.minimarket.pagos_service.repository.PagoRepository;
import com.minimarket.pagos_service.service.PagoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.minimarket.pagos_service.dto.PagoRequestDTO;
import com.minimarket.pagos_service.dto.CompraResponseDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceMockitoTest {

    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private CompraClient compraClient;
    @Mock
    private VentaClient ventaClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void obtenerTodos_deberiaRetornarPagosActivos() {
        Pago pago = new Pago(1L, 1L, 1L, LocalDateTime.now(), 10000.0, MetodoPago.EFECTIVO, EstadoPago.PAGADO, "PED-123456", true);
        when(pagoRepository.findByActivoTrue()).thenReturn(List.of(pago));

        List<PagoResponseDTO> resultado = pagoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("PAGADO", resultado.get(0).getEstado());
        verify(pagoRepository).findByActivoTrue();
    }

    @Test
    void obtenerPorId_deberiaRetornarPagoActivo() {
        Pago pago = new Pago(1L, 1L, 1L, LocalDateTime.now(), 10000.0, MetodoPago.EFECTIVO, EstadoPago.PAGADO, "PED-123456", true);
        when(pagoRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(pago));

        PagoResponseDTO resultado = pagoService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("EFECTIVO", resultado.getMetodoPago());
        verify(pagoRepository).findByIdAndActivoTrue(1L);
    }

    @Test
    void eliminarPago_deberiaRealizarBorradoLogico() {
        Pago pago = new Pago(1L, 1L, 1L, LocalDateTime.now(), 10000.0, MetodoPago.EFECTIVO, EstadoPago.PAGADO, "PED-123456", true);
        when(pagoRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(pago));

        pagoService.eliminarPago(1L);

        assertFalse(pago.isActivo());
        verify(pagoRepository).save(pago);
    }

@Test
    void obtenerPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(pagoRepository.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pagoService.obtenerPorId(99L));
        verify(pagoRepository).findByIdAndActivoTrue(99L);
    }

    @Test
    void guardarPago_deberiaGuardarPagoValido() {
        PagoRequestDTO dto = new PagoRequestDTO(1L, 1L, 10000.0, "EFECTIVO");
        CompraResponseDTO compra = new CompraResponseDTO(1L, 10000.0, "PENDIENTE");
        Pago guardado = new Pago(1L, 1L, 1L, LocalDateTime.now(), 10000.0, MetodoPago.EFECTIVO, EstadoPago.PAGADO, "PED-123456", true);
        when(compraClient.obtenerPorId(1L)).thenReturn(compra);
        when(pagoRepository.existsByReferencia(anyString())).thenReturn(false);
        when(pagoRepository.save(any(Pago.class))).thenReturn(guardado);

        PagoResponseDTO resultado = pagoService.guardarPago(dto);

        assertEquals(1L, resultado.getId());
        assertEquals("PAGADO", resultado.getEstado());
        verify(ventaClient).obtenerPorId(1L);
        verify(compraClient).obtenerPorId(1L);
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void guardarPago_deberiaLanzarExcepcionSiMetodoNoEsValido() {
        PagoRequestDTO dto = new PagoRequestDTO(1L, 1L, 10000.0, "CHEQUE");

        assertThrows(IllegalArgumentException.class, () -> pagoService.guardarPago(dto));
        verify(pagoRepository, never()).save(any(Pago.class));
    }

    @Test
    void actualizarPago_deberiaModificarPago() {
        Pago pago = new Pago(1L, 1L, 1L, LocalDateTime.now(), 10000.0, MetodoPago.EFECTIVO, EstadoPago.PAGADO, "PED-123456", true);
        PagoRequestDTO dto = new PagoRequestDTO(2L, 2L, 15000.0, "TARJETA");
        CompraResponseDTO compra = new CompraResponseDTO(2L, 15000.0, "PENDIENTE");
        when(pagoRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(pago));
        when(compraClient.obtenerPorId(2L)).thenReturn(compra);
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoResponseDTO resultado = pagoService.actualizarPago(1L, dto);

        assertEquals(15000.0, resultado.getMonto());
        assertEquals("TARJETA", resultado.getMetodoPago());
        verify(ventaClient).obtenerPorId(2L);
        verify(pagoRepository).save(pago);
    }
}
