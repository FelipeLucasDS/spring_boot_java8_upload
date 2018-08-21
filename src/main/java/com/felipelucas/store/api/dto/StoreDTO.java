package com.felipelucas.store.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDTO {

    public Long id;
    public String name;
    public String city;
    public String state;
    public String latitude;
    public String longitude;
    public BigDecimal revenue;

    public StoreDTO() {
    }
}
