package com.management.account.accountmanagement.repository;

import com.management.account.accountmanagement.model.SavingsAccount;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Long> {


    String SAVINGS_ACCOUNT = "SELECT * FROM SAVINGS_ACCOUNT SA WHERE SA.ACCOUNT_NUMBER = :accountNumber LIMIT 1;";

    String UPDATE_SAVINGS_ACCOUNT = "UPDATE SAVINGS_ACCOUNT SA SET SA.BALANCE_AMOUNT =:balanceAmount WHERE SA.ACCOUNT_NUMBER =:accountNumber";

    @Query(value = SAVINGS_ACCOUNT, nativeQuery = true)
    SavingsAccount getSavingsAccount(@Param("accountNumber") Long accountNumber) throws DataIntegrityViolationException;

    @Modifying(clearAutomatically = true)
    @Query(value = UPDATE_SAVINGS_ACCOUNT, nativeQuery = true)
    void updateSavingsAccount(@Param("balanceAmount") BigDecimal balanceAmount, @Param("accountNumber") Long accountNumber);


}
