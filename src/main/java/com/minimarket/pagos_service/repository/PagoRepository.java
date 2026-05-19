package com.minimarket.pagos_service.repository;

import com.minimarket.pagos_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByActivoTrue();

    Optional<Pago> findByIdAndActivoTrue(Long id);

    boolean existsByReferencia(String referencia);





}