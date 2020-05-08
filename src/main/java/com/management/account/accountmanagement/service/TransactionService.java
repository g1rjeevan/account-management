package com.management.account.accountmanagement.service;

import com.management.account.accountmanagement.errorhandler.AccountManagementInvalidTransactionException;
import com.management.account.accountmanagement.errorhandler.AccountManagementResourceNotFound;
import com.management.account.accountmanagement.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface TransactionService {

    Transaction getDepositTransaction(final BigDecimal amount, final Long savingsAccountId) throws AccountManagementResourceNotFound;

    Transaction getWithdrawTransaction(final BigDecimal amount, final Long savingsAccountId) throws AccountManagementInvalidTransactionException, AccountManagementResourceNotFound;

    List<Transaction> getLatestTransactionOfTheAccount(Long savingAcountNumber) throws AccountManagementResourceNotFound;
}
