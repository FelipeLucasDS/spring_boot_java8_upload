package com.felipelucas.customer.api.v1;

import com.felipelucas.commons.api.BaseRestController;
import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;


@RestController
public class CustomerAPI extends BaseRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value="/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") Long id) {
        return ok(customerService.getById(id));
    }

    @PutMapping(value="/customer/{id}")
    public ResponseEntity updateCustomer(@PathVariable(value = "id") Long id,
                                      @RequestBody CustomerDTO customerDTO) {
        return ok(customerService.updateCustomer(id, customerDTO));

    }

    @PostMapping(value="/customer")
    public ResponseEntity<Long> createSingleCustomer(@RequestBody CustomerDTO customerDTO) {
        Long idSaved = customerService.createSingleCustomer(customerDTO);
        return ok(idSaved);
    }


    @PostMapping(value="/customer/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity receiveFile(@RequestParam("file") MultipartFile file) {
        customerService.createFromFile(file);
        return ok().build();
    }
}