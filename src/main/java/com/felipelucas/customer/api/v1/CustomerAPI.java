package com.felipelucas.customer.api.v1;

import com.felipelucas.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CustomerAPI {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value="/customer/{id}")
    public ResponseEntity createSingleStore(@PathVariable(value = "id") Long id) {
        customerService.createSingleStore();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/customer/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity receiveFile(@RequestParam("file") MultipartFile file) {
        customerService.createFromFile(file);
        return ResponseEntity.ok().build();
    }
}