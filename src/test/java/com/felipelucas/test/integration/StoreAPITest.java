package com.felipelucas.test.integration;

import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.test.commons.IntegrationTestBase;
import com.felipelucas.test.commons.RestRequest;
import com.felipelucas.test.mock.StoreMockFactory;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;

public class StoreAPITest extends IntegrationTestBase {

    @Value(value = "classpath:csv/stores.csv")
    private Resource storesCSV;

    public void getImportStoreOK() throws Exception {

        ResponseEntity response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/store/import")
                        .execute(new ParameterizedTypeReference<List>() {}, storesCSV.getFile());

        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void createStore() throws Exception {
        ResponseEntity<Long> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .payload(StoreMockFactory.mockFirst())
                        .endpoint("/store")
                        .execute(new ParameterizedTypeReference<Long>() {});

        assertEquals(OK, response.getStatusCode());

        ResponseEntity<StoreDTO> responseGET =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/store/"+response.getBody())
                        .execute(
                                new ParameterizedTypeReference<StoreDTO>() {});

        assertEquals(OK, responseGET.getStatusCode());
        assertEquals(StoreMockFactory.mockFirst(), responseGET.getBody());
    }


    @Test
    public void updateStore() throws Exception {
        ResponseEntity<Long> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .payload(StoreMockFactory.mockFirst())
                        .endpoint("/store")
                        .execute(new ParameterizedTypeReference<Long>() {});

        assertEquals(OK, response.getStatusCode());

        ResponseEntity responsePUT =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(PUT)
                        .payload(StoreMockFactory.mockSeccond())
                        .endpoint("/store/"+response.getBody())
                        .execute();

        assertEquals(OK, responsePUT.getStatusCode());

        ResponseEntity<StoreDTO> responseGET =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/store/"+response.getBody())
                        .execute(
                                new ParameterizedTypeReference<StoreDTO>() {});

        assertEquals(OK, responseGET.getStatusCode());
        assertEquals(StoreMockFactory.mockSeccond(), responseGET.getBody());
    }


}