package com.example.employees_backend.exception;

import com.example.employees_backend.exception.exceptions.CorruptedDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeFormatter;
import java.util.zip.DataFormatException;

@ControllerAdvice
public class EmployeeException {
    @ExceptionHandler({CorruptedDataException.class})
    ResponseEntity<?> handleException (CorruptedDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(e.getMessage());
    }

    @ExceptionHandler({DataFormatException.class})
    ResponseEntity<?> handleException (DataFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    ResponseEntity<?> handleException (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(e.getMessage());
    }
}
