package com.felipelucas.store.service;

import com.felipelucas.commons.exceptions.CSVEmptyException;
import com.felipelucas.commons.exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.commons.csv.CSVProcessor;
import com.felipelucas.commons.csv.CSVProcessorInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StoreCSVProcessor implements CSVProcessorInterface {

    private final static Logger logger = LoggerFactory.getLogger(StoreCSVProcessor.class);

    private final static String csvType = "text/csv";

    @Autowired
    private CSVProcessor csvProcessor;

    @Override
    public void validate(MultipartFile file) throws CSVException, CSVEmptyException {
        csvProcessor.validate(file);
    }

    @Override
    public CSVDTO getCSVData(MultipartFile file) throws CSVException, CSVEmptyException {
        CSVDTO csvdto = csvProcessor.getCSVData(file);
        csvProcessor.validateColumnQtd(csvdto, 6);
        return csvdto;
    }
}
