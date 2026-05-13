package com.minimarket.pagos_service.config;

import com.minimarket.pagos_service.model.EstadoPago;
import com.minimarket.pagos_service.model.MetodoPago;
import com.minimarket.pagos_service.model.Pago;
import com.minimarket.pagos_service.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
//q no se te olvide q implementa commandlinerunner
public class DataInitializer implements CommandLineRunner {

    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) {
        if (pagoRepository.count() > 0) {
            log.info(
                    ">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial."
            );
            return;
        }

        log.info(
                ">>> DataInitializer: BD vacía detectada, insertando datos de prueba..."
        );

        //Como voy a hacer q se conecte con compra y ventas? estes se conecta con varios y compraid y ventaid tambien se conectan con este y también

        Pago pago1 = new Pago(null,1L,1L,LocalDateTime.now(),15990.0,MetodoPago.TARJETA,EstadoPago.PAGADO,"PED-847291",true);

        pagoRepository.save(pago1);

        Pago pago2 = new Pago(
                null,
                2L,
                2L,
                LocalDateTime.now().minusHours(1),
                24990.0,
                MetodoPago.TRANSFERENCIA,
                EstadoPago.PENDIENTE,
                "PED-563874",
                true
        );

        pagoRepository.save(pago2);

        Pago pago3 = new Pago(
                null,
                3L,
                3L,
                LocalDateTime.now().minusDays(1),
                8990.0,
                MetodoPago.EFECTIVO,
                EstadoPago.PAGADO,
                "PED-918245",
                true
        );

        pagoRepository.save(pago3);

        Pago pago4 = new Pago(
                null,
                4L,
                4L,
                LocalDateTime.now().minusMinutes(45),
                45990.0,
                MetodoPago.TARJETA,
                EstadoPago.RECHAZADO,
                "PED-374650",
                true
        );

        pagoRepository.save(pago4);

        Pago pago5 = new Pago(
                null,
                5L,
                5L,
                LocalDateTime.now().minusDays(2),
                12990.0,
                MetodoPago.TRANSFERENCIA,
                EstadoPago.PAGADO,
                "PED-682913",
                true
        );

        pagoRepository.save(pago5);


        //LocalDateTime.now().minusDays(12);

        log.info(
                ">>> DataInitializer: {} pagos insertados.",
                pagoRepository.count()
        );
    }
}
