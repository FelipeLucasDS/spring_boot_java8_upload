package com.felipelucas.store.api.parser;

import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.store.domain.Store;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreParser {

    private Logger logger = LoggerFactory.getLogger(StoreParser.class);

    public List<Store> toEntity(CSVDTO csv) {

        logger.info("Starting parser from csv with {} lines", csv.getCsvData().size());

        List<Store> stores = csv.getCsvData().stream()
                .map(line -> {
                    return new StoreBuilder()
                            .setName(line.removeFirst())
                            .setCity(line.removeFirst())
                            .setState(line.removeFirst())
                            .setLatitude(line.removeFirst())
                            .setLongitude(line.removeFirst())
                            .setRevenue(line.removeFirst())
                            .getitem();
                }).collect(Collectors.toList());

        logger.info("Parsing of csv finished", csv.getCsvData().size());

        return stores;
    }

    private class StoreBuilder{
        Store store;

        StoreBuilder(){
            this.store = new Store();
        }

        StoreBuilder setName(String name) {
            store.setName(name);
            return this;
        }

        StoreBuilder setCity(String city) {
            store.setCity(city);
            return this;
        }

        StoreBuilder setState(String state) {
            store.setState(state);
            return this;
        }

        StoreBuilder setLatitude(String latitude) {
            store.setLatitude(latitude);
            return this;
        }

        StoreBuilder setLongitude(String longitude) {
            store.setLongitude(longitude);
            return this;
        }

        StoreBuilder setRevenue(String revenue) {
            store.setRevenue(new BigDecimal(revenue));
            return this;
        }

        Store getitem(){
            return this.store;
        }
    }

}