package com.felipelucas.customer.service.parser;

import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.customer.domain.Customer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerParser {

    private Logger logger = LoggerFactory.getLogger(CustomerParser.class);

    public List<Customer> toEntity(CSVDTO csv) {

        logger.info("Starting parser from csv with {} lines", csv.getCsvData().size());

        List<Customer> customers = csv.getCsvData().stream()
                .map(line -> {
                    return new CustomerBuilder()
                            .setName(line.removeFirst())
                            .setCity(line.removeFirst())
                            .setState(line.removeFirst())
                            .setLatitude(line.removeFirst())
                            .setLongitude(line.removeFirst())
                            .getitem();
                }).collect(Collectors.toList());

        logger.info("Parsing of csv finished", csv.getCsvData().size());

        return customers;
    }


    public Customer toEntity(Customer customer, CustomerDTO customerDTO) {
        if(StringUtils.isNotEmpty(customerDTO.city))
            customer.setCity(customerDTO.city);

        if(StringUtils.isNotEmpty(customerDTO.latitude))
            customer.setLatitude(customerDTO.latitude);

        if(StringUtils.isNotEmpty(customerDTO.longitude))
            customer.setLongitude(customerDTO.longitude);

        if(StringUtils.isNotEmpty(customerDTO.name))
            customer.setName(customerDTO.name);

        if(StringUtils.isNotEmpty(customerDTO.state))
            customer.setState(customerDTO.state);

        return customer;
    }


    public CustomerDTO toDTO(Customer customer){
        return new CustomerDTOBuilder()
                .setID(customer.getId())
                .setCity(customer.getCity())
                .setLatitude(customer.getLatitude())
                .setLongitude(customer.getLongitude())
                .setName(customer.getName())
                .setState(customer.getState())
                .getitem();
    }

    private class CustomerBuilder {
        Customer customer;

        CustomerBuilder(){
            this.customer = new Customer();
        }

        CustomerBuilder setID(Long id) {
            customer.setId(id);
            return this;
        }


        CustomerBuilder setName(String name) {
            customer.setName(name);
            return this;
        }

        CustomerBuilder setCity(String city) {
            customer.setCity(city);
            return this;
        }

        CustomerBuilder setState(String state) {
            customer.setState(state);
            return this;
        }

        CustomerBuilder setLatitude(String latitude) {
            customer.setLatitude(latitude);
            return this;
        }

        CustomerBuilder setLongitude(String longitude) {
            customer.setLongitude(longitude);
            return this;
        }

        Customer getitem(){
            return this.customer;
        }
    }


    private class CustomerDTOBuilder {
        CustomerDTO customerDTO;

        CustomerDTOBuilder(){
            this.customerDTO = new CustomerDTO();
        }

        CustomerDTOBuilder setID(Long id) {
            customerDTO.id = id;
            return this;
        }

        CustomerDTOBuilder setName(String name) {
            customerDTO.name = name;
            return this;
        }

        CustomerDTOBuilder setCity(String city) {
            customerDTO.city = city;
            return this;
        }

        CustomerDTOBuilder setState(String state) {
            customerDTO.state = state;
            return this;
        }

        CustomerDTOBuilder setLatitude(String latitude) {
            customerDTO.latitude = latitude;
            return this;
        }

        CustomerDTOBuilder setLongitude(String longitude) {
            customerDTO.longitude = longitude;
            return this;
        }

        CustomerDTO getitem(){
            return this.customerDTO;
        }
    }

}