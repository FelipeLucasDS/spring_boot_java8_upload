package com.felipelucas.store.service;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.commons.csv.CSVProcessor;
import com.felipelucas.store.api.parser.StoreParser;
import com.felipelucas.store.domain.Store;
import com.felipelucas.store.repository.StoreRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StoreService {

    private final static Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StoreParser parser;

    @Autowired
    private StoreCSVProcessor storeCSVProcessor;

    @Autowired
    private StoreRepository repository;

    @Transactional
    public void createSingleStore() {

        Store store = new Store();
        store = repository.save(store);

        logger.info("Creating a new store");

        logger.info("Store {} created");

    }

    @Transactional
    public void readSingleStore(MultipartFile multipart) throws CSVEmptyException, CSVException {
        storeCSVProcessor.validate(multipart);

        CSVDTO csvData = storeCSVProcessor.getCSVData(multipart);
        List<Store> stores = parser.toEntity(csvData);

        repository.save(stores);
    }
}