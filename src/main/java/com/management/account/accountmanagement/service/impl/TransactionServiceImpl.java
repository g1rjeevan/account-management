package com.management.account.accountmanagement.service.impl;

import com.management.account.accountmanagement.errorhandler.AccountManagementInvalidTransactionException;
import com.management.account.accountmanagement.errorhandler.AccountManagementResourceNotFound;
import com.management.account.accountmanagement.model.SavingsAccount;
import com.management.account.accountmanagement.model.Transaction;
import com.management.account.accountmanagement.repository.SavingsAccountRepository;
import com.management.account.accountmanagement.repository.TransactionRepostiory;
import com.management.account.accountmanagement.service.TransactionService;
import com.management.account.accountmanagement.utils.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionRepostiory transactionRepostiory;

    @Override
    public Transaction getDepositTransaction(final BigDecimal amount, final Long savingsAccountId) throws AccountManagementResourceNotFound {
        final SavingsAccount savingsAccount;
        LOGGER.info("Coming here");

        try {
            savingsAccount = savingsAccountRepository.getSavingsAccount(savingsAccountId);
        } catch (Exception ex) {
            LOGGER.info("Coming here");
                throw new AccountManagementResourceNotFound("Savings Account",  savingsAccountId.toString());
        }
        LOGGER.info("Coming here"+savingsAccount.toString());

            Transaction transaction = new Transaction();
            transaction.setAmount(amount.doubleValue());
            transaction.setType(TransactionType.DEPOSIT.name());
            transaction.setSavingsAccount(savingsAccount);
            transaction.setAvailableBalance(savingsAccount.getBalanceAmount().add(new BigDecimal(transaction.getAmount())));
            savingsAccountRepository.updateSavingsAccount(transaction.getAvailableBalance(), savingsAccountId);
            transaction = transactionRepostiory.save(transaction);
            return transaction;

    }

    @Override
    public Transaction getWithdrawTransaction(final BigDecimal amount, final Long savingsAccountId) throws AccountManagementInvalidTransactionException, AccountManagementResourceNotFound  {
        final SavingsAccount savingsAccount;
        try {
            savingsAccount = savingsAccountRepository.getSavingsAccount(savingsAccountId);
        } catch (Exception ex) {
            throw new AccountManagementResourceNotFound("Savings Account", savingsAccountId.toString());

        }
        final Transaction transaction = new Transaction();
        transaction.setAmount(amount.doubleValue());
        transaction.setType(TransactionType.WITHDRAW.name());
        transaction.setSavingsAccount(savingsAccount);
        BigDecimal diffAmount = savingsAccount.getBalanceAmount().subtract(new BigDecimal(transaction.getAmount()));
        if (diffAmount.doubleValue() >= 0) {
            transaction.setAvailableBalance(diffAmount);
        } else {
            throw new AccountManagementInvalidTransactionException(TransactionType.WITHDRAW.name(), amount.toString());
        }
        savingsAccountRepository.updateSavingsAccount(transaction.getAvailableBalance(), savingsAccountId);
        return transactionRepostiory.save(transaction);

    }

    @Override
    public List<Transaction> getLatestTransactionOfTheAccount(Long savingAccountNumber) throws AccountManagementResourceNotFound {
        final SavingsAccount savingsAccount;
        try {
            savingsAccount = savingsAccountRepository.getSavingsAccount(savingAccountNumber);
            return transactionRepostiory.getLatestTenTransactions(savingsAccount.getId());
        } catch (Exception ex) {
            throw new AccountManagementResourceNotFound("Savings Account",  savingAccountNumber.toString());

        }
    }


}
