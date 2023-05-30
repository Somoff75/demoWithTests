package com.example.demowithtests.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<?> employeeAlreadyExistsException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Employee already exists."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<?> invalidEmployeeException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("All fields must be filled in."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmployeesNotFoundException.class)
    public ResponseEntity<?> employeesNotFoundException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Employees not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseAccessException.class)
    public ResponseEntity<?> databaseAccessException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Database access error."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IdEmployeeNotFoundException.class)
    public ResponseEntity<?> idEmployeeNotFoundException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Employee not found."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeletedEmployeeException.class)
    public ResponseEntity<?> deletedEmployeeException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Employee was deleted."), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> invalidRequestException(){
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Invalid request ") , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidWorkplaceSizeException.class)
    public ResponseEntity<?> invalidWorkplaceSizeException(){
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Invalid workplace size. ") , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoAvailableWorkplacesException.class)
    public ResponseEntity<?> noAvailableWorkplacesException(){
        return new ResponseEntity<>(new MyGlobalExceptionHandler("No available workplaces"), HttpStatus.NOT_FOUND);
    }


    @Data
    private static class MyGlobalExceptionHandler {
        private String message;

        public MyGlobalExceptionHandler(String message) {
            this.message = message;
        }
    }
}
