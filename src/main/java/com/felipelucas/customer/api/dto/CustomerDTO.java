package com.felipelucas.customer.api.dto;

import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.domain.Store;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

public class CustomerDTO {

    public Long id;
    public String name;
    public String city;
    public String state;
    public String latitude;
    public String longitude;
    public StoreDTO store;

    public CustomerDTO() {}
}
