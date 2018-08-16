package com.felipelucas.infra;

import com.felipelucas.commons.Exceptions.CSVEmptyException;
import com.felipelucas.commons.Exceptions.CSVException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({CSVEmptyException.class})
    public ResponseEntity<String> handleCSVEmptyException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({CSVException.class})
    public ResponseEntity<String> handleCSVException(CSVException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}