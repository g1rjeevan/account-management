package com.management.account.accountmanagement.controller;

import com.management.account.accountmanagement.errorhandler.AccountManagementInvalidTransactionException;
import com.management.account.accountmanagement.errorhandler.AccountManagementResourceNotFound;
import com.management.account.accountmanagement.model.Transaction;
import com.management.account.accountmanagement.repository.TransactionRepostiory;
import com.management.account.accountmanagement.service.TransactionService;
import com.management.account.accountmanagement.utils.TransactionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Api(tags = "Transactions API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/transaction")
public class TransactionController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionRepostiory transactionRepostiory;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Iterable<Transaction> getAllTransaction() {
        return transactionRepostiory.findAll();
    }

    @ApiOperation(value = "Returns the latest 10 transaction of the savings account for the given account number.")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")
    })
    @RequestMapping(value = "/latest/{accountNumber}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<Transaction> getLatestTransactionOfTheAccount(@PathVariable Long accountNumber) throws Exception {
        return transactionService.getLatestTransactionOfTheAccount(accountNumber);
    }

    @ApiOperation(value = "Used for amount withdraw and deposit using this api by specifying the {transaction_type}, in request body with amount and accountNumber.")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")
    })
    @RequestMapping(value = "/{transaction_type}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @Transactional
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Transaction addSavingsAccount(@PathVariable("transaction_type") String transaction_type,
                                         @RequestBody Map<String, Object> transactionRequestBody) throws AccountManagementInvalidTransactionException, AccountManagementResourceNotFound{

        Transaction transaction = null;
        final BigDecimal amount = new BigDecimal(transactionRequestBody.get("amount").toString());
        final Long accountNumber = Long.valueOf(transactionRequestBody.get("accountNumber").toString());
        if (transaction_type.equalsIgnoreCase(TransactionType.DEPOSIT.name())) {
            transaction = transactionService.getDepositTransaction(amount, accountNumber);
        } else if (transaction_type.equalsIgnoreCase(TransactionType.WITHDRAW.name())) {
            transaction = transactionService.getWithdrawTransaction(amount, accountNumber);
        }
        return transaction;
    }
}