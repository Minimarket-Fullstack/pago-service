package com.minimarket.pagos_service.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data

public class PagoRequestDTO {

    @NotNull
    private Long id;

    private LocalDateTime fechaPago;

    

}
