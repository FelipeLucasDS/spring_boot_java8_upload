package com.felipelucas.store.service.parser;

import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.domain.Store;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreParser {

    private Logger logger = LoggerFactory.getLogger(StoreParser.class);

    public StoreDTO fromEntity(Store store) {
        if(Objects.isNull(store))
            return null;

        return new StoreDTOBuilder()
                .setId(store.getId())
                .setName(store.getName())
                .setCity(store.getCity())
                .setState(store.getState())
                .setLatitude(store.getLatitude())
                .setLongitude(store.getLongitude())
                .setRevenue(store.getRevenue())
                .getitem();
    }

    public Store toEntity(Store store, StoreDTO storeDTO) {
        if(Objects.nonNull(storeDTO.revenue))
            store.setRevenue(storeDTO.revenue);

        if(StringUtils.isNotEmpty(storeDTO.city))
            store.setCity(storeDTO.city);

        if(StringUtils.isNotEmpty(storeDTO.latitude))
            store.setLatitude(Double.valueOf(storeDTO.latitude));

        if(StringUtils.isNotEmpty(storeDTO.longitude))
            store.setLongitude(Double.valueOf(storeDTO.longitude));

        if(StringUtils.isNotEmpty(storeDTO.name))
            store.setName(storeDTO.name);

        if(StringUtils.isNotEmpty(storeDTO.state))
            store.setState(storeDTO.state);

        return store;
    }


    public Store toEntity(StoreDTO storeDTO) {
        return new StoreBuilder()
                .setId(storeDTO.id)
                .setName(storeDTO.name)
                .setCity(storeDTO.city)
                .setState(storeDTO.state)
                .setLatitude(storeDTO.latitude)
                .setLongitude(storeDTO.longitude)
                .setRevenue(storeDTO.revenue)
                .getitem();
    }

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
        private Store store;

        StoreBuilder(){
            this.store = new Store();
        }

        StoreBuilder setId(Long id) {
            store.setId(id);
            return this;
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
            store.setLatitude(Double.valueOf(latitude));
            return this;
        }

        StoreBuilder setLongitude(String longitude) {
            store.setLongitude(Double.valueOf(longitude));
            return this;
        }

        StoreBuilder setRevenue(String revenue) {
            store.setRevenue(new BigDecimal(revenue));
            return this;
        }

        StoreBuilder setRevenue(BigDecimal revenue) {
            store.setRevenue(revenue);
            return this;
        }

        Store getitem(){
            return this.store;
        }
    }


    private class StoreDTOBuilder{
        private StoreDTO store;

        StoreDTOBuilder(){
            this.store = new StoreDTO();
        }

        StoreDTOBuilder setId(Long id) {
            store.id = id;
            return this;
        }

        StoreDTOBuilder setName(String name) {
            store.name = name;
            return this;
        }

        StoreDTOBuilder setCity(String city) {
            store.city = city;
            return this;
        }

        StoreDTOBuilder setState(String state) {
            store.state = state;
            return this;
        }

        StoreDTOBuilder setLatitude(Double latitude) {
            store.latitude = latitude.toString();
            return this;
        }

        StoreDTOBuilder setLongitude(Double longitude) {
            store.longitude = longitude.toString();
            return this;
        }

        StoreDTOBuilder setRevenue(String revenue) {
            store.revenue = new BigDecimal(revenue);
            return this;
        }

        StoreDTOBuilder setRevenue(BigDecimal revenue) {
            store.revenue = revenue;
            return this;
        }

        StoreDTO getitem(){
            return this.store;
        }
    }

}