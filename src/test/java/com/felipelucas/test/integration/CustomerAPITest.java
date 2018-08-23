package com.felipelucas.test.integration;

import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.test.commons.IntegrationTestBase;
import com.felipelucas.test.commons.RestRequest;
import com.felipelucas.test.mock.CustomerMockFactory;
import java.util.List;
import org.junit.Before;
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

public class CustomerAPITest extends IntegrationTestBase {

    @Value(value = "classpath:csv/customers.csv")
    private Resource customersCSV;

    @Value(value = "classpath:csv/stores_far_near.csv")
    private Resource storeFarAndNear;


    @Before
    public void getImportCustomerAndStore() throws Exception {
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
    public void getCustomerAllValidateStore() throws Exception {
        ResponseEntity<List<CustomerDTO>> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/customer")
                        .execute(
                                new ParameterizedTypeReference<List<CustomerDTO>>() {});

        assertEquals(OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(CustomerMockFactory.mockFirstCustomer(), response.getBody().get(0));
        assertEquals(CustomerMockFactory.mockFirstCustomer().store, response.getBody().get(0).store);
    }

    @Test
    public void createCustomer() throws Exception {
        ResponseEntity<Long> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .payload(CustomerMockFactory.mockFirstCustomer())
                        .endpoint("/customer")
                        .execute(new ParameterizedTypeReference<Long>() {});

        assertEquals(OK, response.getStatusCode());

        ResponseEntity<CustomerDTO> responseGET =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/customer/"+response.getBody())
                        .execute(
                                new ParameterizedTypeReference<CustomerDTO>() {});

        assertEquals(OK, responseGET.getStatusCode());
        assertEquals(CustomerMockFactory.mockFirstCustomer(), responseGET.getBody());
    }


    @Test
    public void updateCustomer() throws Exception {
        ResponseEntity<Long> response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .payload(CustomerMockFactory.mockFirstCustomer())
                        .endpoint("/customer")
                        .execute(new ParameterizedTypeReference<Long>() {});

        assertEquals(OK, response.getStatusCode());

        ResponseEntity responsePUT =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(PUT)
                        .payload(CustomerMockFactory.mockSeccondCustomer())
                        .endpoint("/customer/"+response.getBody())
                        .execute();

        assertEquals(OK, responsePUT.getStatusCode());

        ResponseEntity<CustomerDTO> responseGET =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/customer/"+response.getBody())
                        .execute(
                                new ParameterizedTypeReference<CustomerDTO>() {});

        assertEquals(OK, responseGET.getStatusCode());
        assertEquals(CustomerMockFactory.mockSeccondCustomer(), responseGET.getBody());
    }


}