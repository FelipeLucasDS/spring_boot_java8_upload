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

    private Long id;
    private String name;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private StoreDTO store;

    public CustomerDTO() {}
}
