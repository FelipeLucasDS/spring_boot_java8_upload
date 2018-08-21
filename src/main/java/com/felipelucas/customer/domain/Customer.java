package com.felipelucas.customer.domain;

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

@Entity
@Table(name = "Customer", schema = "PERFORMANCE")
public class    Customer {

    @Id
    @GenericGenerator(strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            name = "Store_SEQ", parameters = {
            @Parameter(name = "sequence_name", value = "Store_SEQ"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(generator="Store_SEQ")
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;


    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Customer(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
