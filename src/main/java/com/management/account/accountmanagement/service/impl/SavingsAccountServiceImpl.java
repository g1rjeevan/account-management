package com.management.account.accountmanagement.service.impl;

import com.management.account.accountmanagement.errorhandler.AccountManagementResourceNotFound;
import com.management.account.accountmanagement.model.SavingsAccount;
import com.management.account.accountmanagement.repository.SavingsAccountRepository;
import com.management.account.accountmanagement.repository.TransactionRepostiory;
import com.management.account.accountmanagement.service.SavingsAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    final Logger LOGGER = LoggerFactory.getLogger(SavingsAccountService.class);

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionRepostiory transactionRepostiory;

    @Override
    public void deleteSavingsAccountWithTransaction(Long accountNumber) throws Exception {
        try {
            final SavingsAccount savingsAccount = savingsAccountRepository.getSavingsAccount(accountNumber);
            transactionRepostiory.deleteTransactionsByAccountId(savingsAccount.getId());
            savingsAccountRepository.deleteById(savingsAccount.getId());
            LOGGER.info("Savings Account has been deleted.");
        } catch (Exception e) {
            throw new AccountManagementResourceNotFound("Savings Account", accountNumber.toString());
        }
    }

    @Override
    public Map<String, Object> getBalanceOfSavingsAccount(Long accountNumber) throws Exception {
        try {
            final SavingsAccount savingsAccount = savingsAccountRepository.getSavingsAccount(accountNumber);
            LOGGER.info(savingsAccount.toString());
            final Map responseMap = new HashMap<>();
            responseMap.put("balance", savingsAccount.getBalanceAmount().doubleValue());
            LOGGER.info("Savings Account data has been fetched.");
            return responseMap;
        } catch (Exception e) {
            throw new AccountManagementResourceNotFound("Savings Account", accountNumber.toString());
        }
    }
}
