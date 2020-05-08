package com.management.account.accountmanagement.repository;

import com.management.account.accountmanagement.model.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepostiory extends CrudRepository<Transaction, Long> {

    String GET_LATEST_TEN_TRANSACTIONS = "SELECT * FROM TRANSACTION TX WHERE TX.SAVINGS_ACCOUNT_ID =:savingsAccountId LIMIT 10;";

    String DELETE_TRANSACTIONS = "DELETE TRANSACTION TX WHERE TX.SAVINGS_ACCOUNT_ID =:savingsAccountId";

    @Query(value = GET_LATEST_TEN_TRANSACTIONS, nativeQuery = true)
    List<Transaction> getLatestTenTransactions(@Param("savingsAccountId") Long savingsAccountId);


    @Modifying(clearAutomatically = true)
    @Query(value = DELETE_TRANSACTIONS, nativeQuery = true)
    void deleteTransactionsByAccountId(@Param("savingsAccountId") Long savingsAccountId);

}
