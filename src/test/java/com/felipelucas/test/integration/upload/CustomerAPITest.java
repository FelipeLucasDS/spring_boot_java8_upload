package com.felipelucas.test.integration.upload;

import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.test.commons.IntegrationTestBase;
import com.felipelucas.test.commons.RestRequest;
import com.felipelucas.test.mock.CustomerMockFactory;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

public class CustomerAPITest extends IntegrationTestBase {

    @Value(value = "classpath:csv/stores_empty.csv")
    private Resource storesCSVEmpty;

    @Value(value = "classpath:csv/stores.csv")
    private Resource storesCSV;

    @Value(value = "classpath:csv/customers.csv")
    private Resource customersCSV;

    @Value(value = "classpath:others/something_that_is_empty_and_not_CSV.empty")
    private Resource otherFile;

    @Test
    public void getImportCustomerOK() throws Exception {

        ResponseEntity response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/customer/import")
                        .execute(new ParameterizedTypeReference<List>() {}, customersCSV.getFile());

        assertEquals(OK, response.getStatusCode());

        ResponseEntity<List<CustomerDTO>> responseGET =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(GET)
                        .endpoint("/customer")
                        .execute(new ParameterizedTypeReference<List<CustomerDTO>>() {});

        assertEquals(OK, responseGET.getStatusCode());
        assertEquals(100, responseGET.getBody().size());
        assertEquals(CustomerMockFactory.mockFirstCustomer(), responseGET.getBody().get(0));

    }

    @Test
    public void getImportCustomerEmptyNOK() throws Exception {

        ResponseEntity response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/customer/import")
                        .execute(new ParameterizedTypeReference<List>() {}, storesCSVEmpty.getFile());


        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getImportOtherFileNOK() throws Exception {

        ResponseEntity response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/customer/import")
                        .execute(new ParameterizedTypeReference<List>() {}, otherFile.getFile());

        assertEquals(UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }


    @Test
    public void getImportStoreFileNOK() throws Exception {

        ResponseEntity response =
                RestRequest.build()
                        .baseUrl(getBaseUrl())
                        .method(POST)
                        .endpoint("/customer/import")
                        .execute(new ParameterizedTypeReference<List>() {}, storesCSV.getFile());

        assertEquals(PRECONDITION_FAILED, response.getStatusCode());
    }

}