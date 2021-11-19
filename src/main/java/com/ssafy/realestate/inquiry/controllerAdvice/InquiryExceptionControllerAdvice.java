package com.ssafy.realestate.inquiry.controllerAdvice;

import com.ssafy.realestate.inquiry.exception.NoInquiryIdException;
import com.ssafy.realestate.inquiry.exception.NoInquiryUserIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ssafy.realestate.inquiry.controller")
@Slf4j
public class InquiryExceptionControllerAdvice {

    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(NoInquiryIdException.class)
    public ResponseEntity<String> NoInquiryIdException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoInquiryUserIdException.class)
    public ResponseEntity<String> NoInquiryUserIdException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 에러.");
    }
}
