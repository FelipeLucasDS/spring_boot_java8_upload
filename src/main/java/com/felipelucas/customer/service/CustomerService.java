package com.felipelucas.customer.service;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.customer.api.parser.CustomerParser;
import com.felipelucas.customer.domain.Customer;
import com.felipelucas.customer.repository.CustomerRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CustomerService {

    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerParser parser;

    @Autowired
    private CustomerCSVProcessor storeCSVProcessor;

    @Autowired
    private CustomerRepository repository;

    @Transactional
    public void createSingleStore() {

        Customer customer = new Customer();
        customer = repository.save(customer);

        logger.info("Creating a new customer");

        logger.info("Customer {} created");

    }

    @Transactional
    public void createFromFile(MultipartFile multipart) throws CSVEmptyException, CSVException {
        storeCSVProcessor.validate(multipart);

        CSVDTO csvData = storeCSVProcessor.getCSVData(multipart);
        List<Customer> stores = parser.toEntity(csvData);

        repository.save(stores);
    }
}