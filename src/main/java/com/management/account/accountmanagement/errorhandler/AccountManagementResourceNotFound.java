package com.management.account.accountmanagement.errorhandler;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountManagementResourceNotFound extends Exception {
    private String fieldName;
    private Object fieldValue;

    public AccountManagementResourceNotFound(String fieldName, String fieldValue) {
        super(String.format("%s not found with : '%s'", fieldName, fieldValue));
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
