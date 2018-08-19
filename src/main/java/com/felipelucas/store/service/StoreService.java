package com.felipelucas.store.service;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.store.service.parser.StoreParser;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.domain.Store;
import com.felipelucas.store.repository.StoreRepository;
import com.felipelucas.store.service.validator.StoreValidator;
import java.util.List;
import java.util.stream.Collectors;
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
    private StoreValidator storeValidator;

    @Autowired
    private StoreParser parser;

    @Autowired
    private StoreCSVProcessor storeCSVProcessor;

    @Autowired
    private StoreRepository repository;

    @Transactional(readOnly = true)
    public List<StoreDTO> getAll() {
        logger.info("Searching all stores");

        List<Store> all = repository.findAll();
        return all.stream()
                .map(parser::fromEntity)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public StoreDTO getById(Long id) {
        logger.info("Searching store {}", id);

        Store store = repository.findOne(id);
        return parser.fromEntity(store);
    }

    @Transactional
    public Long createSingleStore(StoreDTO storeDTO) {
        storeValidator.validateStore(storeDTO);

        logger.info("Creating a new store");

        Store store =  parser.toEntity(storeDTO);
        store = repository.save(store);

        logger.info("Store {} created", store.getId());
        return store.getId();
    }

    @Transactional
    public Long updateStore(Long id, StoreDTO storeDTO) {
        storeValidator.validateStore(storeDTO);

        logger.info("Updating store {}", id);

        Store repoStore = repository.getOne(id);
        Store store =  parser.toEntity(repoStore, storeDTO);

        logger.info("Store {} updated", store.getId());
        return store.getId();
    }

    @Transactional
    public void readSingleStore(MultipartFile multipart) throws CSVEmptyException, CSVException {
        storeCSVProcessor.validate(multipart);

        CSVDTO csvData = storeCSVProcessor.getCSVData(multipart);
        List<Store> stores = parser.toEntity(csvData);

        repository.save(stores);
    }
}