package com.felipelucas.commons.csv;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CSVProcessor implements CSVProcessorInterface {

    private final static Logger logger = LoggerFactory.getLogger(CSVProcessor.class);

    private final static String csvType = "text/csv";

    @Override
    public void validate(MultipartFile file) throws CSVException, CSVEmptyException {
        if(Objects.isNull(file) || file.isEmpty())
            throw new CSVEmptyException();

        if(!csvType.equals(file.getContentType()))
            throw new CSVException();


    }

    @Override
    public CSVDTO getCSVData(MultipartFile file) throws CSVException, CSVEmptyException {
        CSVDTO csvdto = CSVDTO.build();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            br.lines()
                    .skip(1)
                    .filter(StringUtils::isNotEmpty)
                    .forEach(line->
                            csvdto.addNewLine().addLineInfo(line)
                    );
        } catch (IOException e) {
            logger.error("Had a IOException getting CSV data", e);
            throw new CSVException();
        }

        return csvdto;
    }
}
