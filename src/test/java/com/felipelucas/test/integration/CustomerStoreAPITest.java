package com.felipelucas.test.integration;

import com.felipelucas.commons.dto.ValueDTO;
import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.test.commons.IntegrationTestBase;
import com.felipelucas.test.commons.RestRequest;
import com.felipelucas.test.mock.CustomerMockFactory;
import com.felipelucas.test.mock.StoreMockFactory;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;

public class CustomerStoreAPITest extends IntegrationTestBase {

    @Value(value = "classpath:csv/customers.csv")
    private Resource customersCSV;

    @Value(value = "classpath:csv/stores_far_near.csv")
    private Resource storeFarAndNear;

    private static boolean started = false;

    @Before
    public void getImportCustomerAndStore() throws Exception {
        if(started)
            return;

        started=true;
        ResponseEntity response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/store/import")
                        .execute(new ParameterizedTypeReference<List>() {}, storeFarAndNear.getFile());
        assertEquals(OK, response.getStatusCode());
        response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/customer/import")
                        .execute(new ParameterizedTypeReference<List>() {}, customersCSV.getFile());

        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void validateIsGettingNearAndCustomersCalculation() throws Exception {
        ResponseEntity<List<StoreDTO>> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/store")
                        .execute(
                                new ParameterizedTypeReference<List<StoreDTO>>() {});

        assertEquals(OK, response.getStatusCode());
        List<StoreDTO> storeDTOList = response.getBody();
        assertFalse(storeDTOList.isEmpty());
        assertEquals(StoreMockFactory.mockNear(), storeDTOList.get(0));
        assertTrue(StoreMockFactory.mockNear().mediumConsumption
                .compareTo(storeDTOList.get(0).mediumConsumption) == 0);
    }

    @Test
    public void countTotalCustomers() throws Exception {
        ResponseEntity<ValueDTO> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/customer/countCustomers")
                        .execute(
                                new ParameterizedTypeReference<ValueDTO>() {});

        assertEquals(OK, response.getStatusCode());
        Integer value = (Integer) response.getBody().value;
        assertTrue(value.compareTo(100)==0);
    }

    @Test
    public void getTotalRevenue() throws Exception {
        ResponseEntity<ValueDTO<BigDecimal>> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/store/totalRevenue")
                        .execute(
                                new ParameterizedTypeReference<ValueDTO<BigDecimal>>() {});

        assertEquals(OK, response.getStatusCode());
        BigDecimal value = response.getBody().value;
        assertTrue(value.compareTo(new BigDecimal(20000))==0);
    }

    @Test
    public void getMediumRevenue() throws Exception {
        ResponseEntity<ValueDTO<BigDecimal>> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/store/mediumRevenue")
                        .execute(
                                new ParameterizedTypeReference<ValueDTO<BigDecimal>>() {});

        assertEquals(OK, response.getStatusCode());
        BigDecimal value = response.getBody().value;
        assertTrue(value.compareTo(new BigDecimal(200))==0);
    }


}