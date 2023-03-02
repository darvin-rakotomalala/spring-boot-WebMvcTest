package com.poc.mapper;

import com.poc.common.DtoMapper;
import com.poc.model.domain.Payment;
import com.poc.model.dto.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper extends DtoMapper<PaymentDTO, Payment> {

}
