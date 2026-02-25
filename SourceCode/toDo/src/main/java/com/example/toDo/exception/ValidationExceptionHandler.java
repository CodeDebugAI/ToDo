package com.example.toDo.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex){
            Map<String, String> errors= new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach( err->
                    errors.put(err.getField(),err.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(UnrecognizedPropertyException.class)
        public ResponseEntity<String> handleUnknownFields(UnrecognizedPropertyException ex) {
            String message = "Unknown field: " + ex.getPropertyName();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
}
