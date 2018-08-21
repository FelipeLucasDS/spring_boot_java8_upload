package com.felipelucas.test.mock;

import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.customer.domain.Customer;

public class CustomerMockFactory {

    public static CustomerDTO mockFirstCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.name = "Cliente 1";
        customerDTO.city = "São Paulo";
        customerDTO.state = "SP";
        customerDTO.latitude = "-23.5647971541";
        customerDTO.longitude = "-46.6853087445";
        return customerDTO;
    }

    public static CustomerDTO mockSeccondCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.name = "Cliente 2";
        customerDTO.city = "São Paulo";
        customerDTO.state = "SP";
        customerDTO.latitude = "-23.5582045967";
        customerDTO.longitude = "-46.6388638235";
        return customerDTO;
    }

}
