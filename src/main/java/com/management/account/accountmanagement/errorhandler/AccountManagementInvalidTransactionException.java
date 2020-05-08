package com.management.account.accountmanagement.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AccountManagementInvalidTransactionException extends Exception {

    private String fieldName;
    private Object fieldValue;

    public AccountManagementInvalidTransactionException(String fieldName, String fieldValue) {
        super(String.format("%s insufficent balance : '%s'", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
