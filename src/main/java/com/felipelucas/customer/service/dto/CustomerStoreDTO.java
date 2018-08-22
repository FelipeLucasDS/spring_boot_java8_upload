package com.felipelucas.customer.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerStoreDTO {

    public Long storeID;
    public BigDecimal count;

    public CustomerStoreDTO(Long storeID, Long count) {
        this.storeID = storeID;
        this.count = new BigDecimal(count);
    }

}
