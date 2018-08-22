package com.felipelucas.store.service;

import com.felipelucas.commons.exceptions.CSVEmptyException;
import com.felipelucas.commons.exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.customer.service.CustomerService;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.domain.Store;
import com.felipelucas.store.repository.StoreRepository;
import com.felipelucas.store.service.parser.StoreParser;
import com.felipelucas.store.service.validator.StoreValidator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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

    @Autowired
    private CustomerService customerService;

    @Transactional(readOnly = true)
    public List<StoreDTO> getAll() {
        logger.info("Searching all stores");
        List<Store> all = repository.findAll();

        logger.info("Found {} stores", all.size());

        List<StoreDTO> stores = all.stream()
                .map(parser::fromEntity)
                .collect(Collectors.toList());

        Map<Long, BigDecimal> mapStorePerCustomers = customerService.customersPerStore();

        stores.forEach(storeDTO -> {
            BigDecimal customers = mapStorePerCustomers.get(storeDTO.id);
            storeDTO.mediumConsumption =
                    this.getBigDecimalDivision(
                            customers,
                            storeDTO.revenue);
        });

        return stores;
    }


    @Transactional(readOnly = true)
    public StoreDTO getById(Long id) {
        logger.info("Searching store {}", id);

        Store store = repository.findOne(id);

        StoreDTO storeDTO = parser.fromEntity(store);
        BigDecimal qtdCustomers = customerService.getQtdTotalCustomers(store.getId()).value;
        BigDecimal revenue = storeDTO.revenue;

        storeDTO.mediumConsumption = getBigDecimalDivision(qtdCustomers, revenue);

        return storeDTO;
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
    public void createFromFile(MultipartFile multipart) throws CSVEmptyException, CSVException {
        storeCSVProcessor.validate(multipart);

        CSVDTO csvData = storeCSVProcessor.getCSVData(multipart);
        List<Store> stores = parser.toEntity(csvData);

        repository.save(stores);
    }

    @Transactional(readOnly = true)
    public StoreDTO getNearbyStore(String lat, String lon) {
        logger.info("Searching store by lat {} and long {}", lat, lon);
        List<Store> nearbyStore = null;
        try {
            nearbyStore = repository.getNearbyStore(Double.valueOf(lat), Double.valueOf(lon));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        if(CollectionUtils.isEmpty(nearbyStore)){
            return null;
        }
        return parser.fromEntity(nearbyStore.get(0));
    }

    private BigDecimal getBigDecimalDivision(BigDecimal qtdCustomers, BigDecimal revenue) {
        if(Objects.nonNull(qtdCustomers)
                && Objects.nonNull(revenue)
                && BigDecimal.ZERO.compareTo(qtdCustomers) != 0
                && BigDecimal.ZERO.compareTo(revenue) != 0) {
            return revenue.divide(qtdCustomers, MathContext.DECIMAL128);
        }else{
            return  BigDecimal.ZERO;
        }
    }

}