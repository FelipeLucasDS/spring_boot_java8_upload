package com.felipelucas.customer.service;

import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.commons.dto.ValueDTO;
import com.felipelucas.commons.exceptions.CSVEmptyException;
import com.felipelucas.commons.exceptions.CSVException;
import com.felipelucas.customer.api.dto.CustomerDTO;
import com.felipelucas.customer.domain.Customer;
import com.felipelucas.customer.repository.CustomerRepository;
import com.felipelucas.customer.service.parser.CustomerParser;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.service.StoreService;
import com.felipelucas.store.service.parser.StoreParser;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
    public Long createSingleCustomer(CustomerDTO customerDTO) {
        logger.info("Creating a new customer");

        Customer customer = parser.toEntity(customerDTO);

        customer = repository.save(customer);

        fillCustomerWithNearbyStore(customer);

        logger.info("Customer {} created", customer.getId());
        return customer.getId();

    }

    @Transactional
    public void createFromFile(MultipartFile multipart) throws CSVEmptyException, CSVException {
        storeCSVProcessor.validate(multipart);

        CSVDTO csvData = storeCSVProcessor.getCSVData(multipart);
        List<Customer> customers = parser.toEntity(csvData);

        customers.parallelStream().forEach(this::fillCustomerWithNearbyStore);

        repository.save(customers);
    }

    private void fillCustomerWithNearbyStore(Customer customer) {
        StoreDTO nearbyStore = this.storeService.getNearbyStore(customer.getLatitude(), customer.getLongitude());
        if(Objects.nonNull(nearbyStore))
            customer.setStore(storeParser.toEntity(nearbyStore));
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAll() {
        return repository.findAll().stream()
                .map(parser::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO getById(Long id) {
        Customer customer = repository.getOne(id);
        return this.parser.toDTO(customer);
    }

    @Transactional(readOnly = true)
    public ValueDTO<BigDecimal> getQtdTotalCustomers(Long storeID) {
        ValueDTO valueDTO = new ValueDTO<BigDecimal>();
        valueDTO.value = repository.countCustomersPerStore(storeID);
        return valueDTO;
    }

    @Transactional(readOnly = true)
    public ValueDTO<BigDecimal> getQtdTotalCustomers() {
        ValueDTO valueDTO = new ValueDTO<BigDecimal>();
        valueDTO.value = repository.countCustomers();
        return valueDTO;
    }

    @Transactional(readOnly = true)
    public Map<Long, BigDecimal> customersPerStore() {
        return repository.countCustomersPerStore().stream()
                .collect(Collectors.toMap(o->o.storeID, o -> o.count));
    }


    @Transactional
    public Long updateCustomer(Long id, CustomerDTO customerDTO) {
        logger.info("Updating customer {}", id);

        Customer repoCustomer = repository.getOne(id);
        Customer customer =  parser.toEntity(repoCustomer, customerDTO);

        fillCustomerWithNearbyStore(customer);

        logger.info("customer {} updated", customer.getId());
        return customer.getId();
    }
}