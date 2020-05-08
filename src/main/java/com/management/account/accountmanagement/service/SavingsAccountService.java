package com.management.account.accountmanagement.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SavingsAccountService {

    void deleteSavingsAccountWithTransaction(Long accountNumber) throws Exception;

    Map<String, Object> getBalanceOfSavingsAccount(Long accountNumber) throws Exception;

}
