package com.felipelucas.customer.api.parser;

import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.customer.domain.Customer;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
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

    private class CustomerBuilder {
        Customer store;

        CustomerBuilder(){
            this.store = new Customer();
        }

        CustomerBuilder setName(String name) {
            store.setName(name);
            return this;
        }

        CustomerBuilder setCity(String city) {
            store.setCity(city);
            return this;
        }

        CustomerBuilder setState(String state) {
            store.setState(state);
            return this;
        }

        CustomerBuilder setLatitude(String latitude) {
            store.setLatitude(latitude);
            return this;
        }

        CustomerBuilder setLongitude(String longitude) {
            store.setLongitude(longitude);
            return this;
        }

        Customer getitem(){
            return this.store;
        }
    }

}