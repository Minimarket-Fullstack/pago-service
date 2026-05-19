package com.minimarket.pagos_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponseDTO {

    private Long id;
    private Double total;
    private String estado;
}
