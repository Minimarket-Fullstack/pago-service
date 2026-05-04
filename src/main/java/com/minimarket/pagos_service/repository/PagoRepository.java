package com.minimarket.pagos_service.repository;

import com.minimarket.pagos_service.model.Pago;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PagoRepository extends JpaRepository<Pago,Long> {
}
