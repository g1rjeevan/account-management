package com.management.account.accountmanagement.service.impl;

import com.management.account.accountmanagement.model.SavingsAccount;
import com.management.account.accountmanagement.repository.SavingsAccountRepository;
import com.management.account.accountmanagement.repository.TransactionRepostiory;
import com.management.account.accountmanagement.service.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionRepostiory transactionRepostiory;

    @Override
    public void deleteSavingsAccountWithTransaction(Long accountNumber) throws Exception {
        final SavingsAccount savingsAccount = savingsAccountRepository.getSavingsAccount(accountNumber);
        transactionRepostiory.deleteTransactionsByAccountId(savingsAccount.getId());
        savingsAccountRepository.deleteById(savingsAccount.getId());
    }

    @Override
    public Map<String, Object> getBalanceOfSavingsAccount(Long accountNumber) throws Exception{
        final SavingsAccount savingsAccount = savingsAccountRepository.getSavingsAccount(accountNumber);
        final Map responseMap = new HashMap<>();
        responseMap.put("balance",savingsAccount.getBalanceAmount().doubleValue());
        return responseMap;
    }
}
