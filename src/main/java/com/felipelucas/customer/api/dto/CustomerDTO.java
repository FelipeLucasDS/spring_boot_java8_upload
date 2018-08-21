package com.felipelucas.customer.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.domain.Store;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    public Long id;
    public String name;
    public String city;
    public String state;
    public String latitude;
    public String longitude;
    public StoreDTO store;

    public CustomerDTO() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, state, latitude, longitude);
    }
}
