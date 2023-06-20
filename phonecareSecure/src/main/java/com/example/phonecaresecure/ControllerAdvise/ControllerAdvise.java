package com.example.phonecaresecure.ControllerAdvise;

import com.example.phonecaresecure.ApiException.ApiException;
import com.example.phonecaresecure.ApiResponse.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ConcurrentModificationException;

@ControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> ApiException(ApiException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse> NullPointerException(NullPointerException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> MethodArgumentNotValidException(MethodArgumentNotValidException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> DataIntegrityViolationException(DataIntegrityViolationException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)

    public ResponseEntity<ApiResponse> HttpMessageNotReadableException(HttpMessageNotReadableException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }
    @ExceptionHandler(TransactionSystemException.class)

    public ResponseEntity<ApiResponse> TransactionSystemException(TransactionSystemException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }


    @ExceptionHandler(ConcurrentModificationException.class)

    public ResponseEntity<ApiResponse> ConcurrentModificationException(ConcurrentModificationException e){

        String massage= e.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(massage));
    }
}

