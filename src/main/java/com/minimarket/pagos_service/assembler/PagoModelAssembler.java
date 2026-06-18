package com.minimarket.pagos_service.assembler;

import com.minimarket.pagos_service.controller.PagoControllerV2;
import com.minimarket.pagos_service.dto.PagoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoResponseDTO, EntityModel<PagoResponseDTO>> {

    @Override
    public EntityModel<PagoResponseDTO> toModel(PagoResponseDTO pago) {

        return EntityModel.of(
                pago,
                linkTo(methodOn(PagoControllerV2.class)
                        .obtenerPorId(pago.getId()))
                        .withSelfRel(),

                linkTo(methodOn(PagoControllerV2.class)
                        .listarPagos())
                        .withRel("pagos")
        );
    }
}