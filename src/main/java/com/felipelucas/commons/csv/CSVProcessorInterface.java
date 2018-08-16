package com.felipelucas.commons.csv;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import org.springframework.web.multipart.MultipartFile;

public interface CSVProcessorInterface {

    void validate(MultipartFile file) throws CSVException, CSVEmptyException;

    CSVDTO getCSVData(MultipartFile file) throws CSVException, CSVEmptyException;

}
