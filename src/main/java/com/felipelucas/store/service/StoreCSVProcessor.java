package com.felipelucas.store.service;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import com.felipelucas.commons.csv.CSVDTO;
import com.felipelucas.commons.csv.CSVProcessor;
import com.felipelucas.commons.csv.CSVProcessorInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
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

        Optional<LinkedList<String>> linkedList = csvdto.getCsvData().stream()
                .filter(strings -> strings.size() != 6)
                .findAny();

        if(linkedList.isPresent()){
            throw new CSVException();
        }
        return csvdto;
    }
}
