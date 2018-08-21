package com.felipelucas.customer.service;

import com.felipelucas.commons.exceptions.CSVEmptyException;
import com.felipelucas.commons.exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.customer.api.parser.CustomerParser;
import com.felipelucas.customer.domain.Customer;
import com.felipelucas.customer.repository.CustomerRepository;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.service.StoreService;
import com.felipelucas.store.service.parser.StoreParser;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreParser storeParser;

    @Transactional
    public void createSingleStore() {

        logger.info("Creating a new customer");

        Customer customer = new Customer();
        customer = repository.save(customer);


        logger.info("Customer {} created");

    }

    @Transactional
    public void createFromFile(MultipartFile multipart) throws CSVEmptyException, CSVException {
        storeCSVProcessor.validate(multipart);

        CSVDTO csvData = storeCSVProcessor.getCSVData(multipart);
        List<Customer> stores = parser.toEntity(csvData);

        stores.parallelStream().forEach(this::fillCustomerWithnearbyStore);

        repository.save(stores);
    }

    private void fillCustomerWithnearbyStore(Customer store) {
        StoreDTO nearbyStore = this.storeService.getNearbyStore(store.getLatitude(), store.getLongitude());
        if(Objects.nonNull(nearbyStore))
            store.setStore(storeParser.toEntity(nearbyStore));
    }
}