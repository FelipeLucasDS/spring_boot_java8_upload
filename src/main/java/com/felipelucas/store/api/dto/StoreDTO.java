package com.felipelucas.store.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDTO {

    public Long id;
    public String name;
    public String city;
    public String state;
    public String latitude;
    public String longitude;
    public BigDecimal revenue;
    public BigDecimal mediumConsumption;

    public StoreDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDTO storeDTO = (StoreDTO) o;
        return Objects.equals(name, storeDTO.name) &&
                Objects.equals(city, storeDTO.city) &&
                Objects.equals(state, storeDTO.state) &&
                Objects.equals(latitude, storeDTO.latitude) &&
                Objects.equals(longitude, storeDTO.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, state, latitude, longitude);
    }

    @Override
    public String toString() {
        return "CustomerStoreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}
