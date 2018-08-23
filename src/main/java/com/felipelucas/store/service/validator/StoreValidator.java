package com.felipelucas.store.service.validator;

import com.felipelucas.commons.exceptions.BusinessException;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.service.StoreService;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreValidator {

    private final static Logger logger = LoggerFactory.getLogger(StoreValidator.class);

    public void validateStore(StoreDTO storeDTO) {
        if (Objects.isNull(storeDTO)){
            logger.info("Found a null store");
            throw new BusinessException();
        }
        if (StringUtils.isEmpty(storeDTO.city)){
            logger.info("Found a null/empty city");
            throw new BusinessException();
        }
        if (StringUtils.isEmpty(storeDTO.state)){
            logger.info("Found a null/empty state");
            throw new BusinessException();
        }
        if (StringUtils.isEmpty(storeDTO.name)){
            logger.info("Found a null/empty name");
            throw new BusinessException();
        }
        if (StringUtils.isEmpty(storeDTO.longitude)){
            logger.info("Found a null/empty longitude");
            throw new BusinessException();
        }
        if (StringUtils.isEmpty(storeDTO.latitude)){
            logger.info("Found a null/empty latitude");
            throw new BusinessException();
        }

        if (Objects.isNull(storeDTO.revenue)){
            logger.info("Found a null revenue");
            throw new BusinessException();
        }

        //TODO validate store
    }
}
