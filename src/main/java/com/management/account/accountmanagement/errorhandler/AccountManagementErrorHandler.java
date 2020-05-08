package com.management.account.accountmanagement.errorhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountManagementErrorHandler {


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<AccountManagementError> handleRunTimeException(RuntimeException e) {
        return buildResponseEntity(new AccountManagementError(HttpStatus.INTERNAL_SERVER_ERROR, e));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<AccountManagementError> handleSQLException(DataIntegrityViolationException e) {
        return buildResponseEntity(
                new AccountManagementError(HttpStatus.CONFLICT, "SAVINGS ACCOUNT ALREADY EXIST", e));
    }

    @ExceptionHandler({AccountManagementInvalidTransactionException.class})
    public ResponseEntity<AccountManagementError> handleAccountManagementInvalidTransactionException(AccountManagementInvalidTransactionException e) {
        return buildResponseEntity(
                new AccountManagementError(HttpStatus.CONFLICT, "INVALID REQUEST, INSUFFICIENT BALANCE", e));
    }

    @ExceptionHandler({AccountManagementResourceNotFound.class})
    public ResponseEntity<AccountManagementError> handleAccountManagementResourceNotFound(AccountManagementResourceNotFound e) {
        return buildResponseEntity(
                new AccountManagementError(HttpStatus.CONFLICT, "INVALID REQUEST, SAVINGS ACCOUNT DOES NOT EXIST", e));
    }

    private ResponseEntity<AccountManagementError> buildResponseEntity(AccountManagementError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
