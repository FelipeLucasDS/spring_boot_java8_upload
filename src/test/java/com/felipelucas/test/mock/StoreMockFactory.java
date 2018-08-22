package com.felipelucas.test.mock;

import com.felipelucas.store.api.dto.StoreDTO;
import java.math.BigDecimal;

public class StoreMockFactory {

    public static StoreDTO mockFirst(){
        StoreDTO storeDTO = new StoreDTO();

        storeDTO.name = "Alameda Santos";
        storeDTO.city = "São Paulo";
        storeDTO.state = "SP";
        storeDTO.latitude = "-23.568767";
        storeDTO.longitude = "-46.649907";
        storeDTO.revenue = new BigDecimal(29854.12);
        return storeDTO;
    }

    public static StoreDTO mockSeccond(){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.name = "Av Paulista";
        storeDTO.city = "São Paulo";
        storeDTO.state = "SP";
        storeDTO.latitude = "23.565972";
        storeDTO.longitude = "-46.650859";
        storeDTO.revenue = new BigDecimal(31257.23);
        return storeDTO;
    }

    public static StoreDTO mockNear(){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.name = "Alameda Santos";
        storeDTO.city = "São Paulo";
        storeDTO.state = "SP";
        storeDTO.latitude = "-23.568767";
        storeDTO.longitude = "-46.649907";
        storeDTO.revenue = new BigDecimal(10000);
        storeDTO.mediumConsumption = new BigDecimal(50);
        return storeDTO;
    }

}
