package com.management.account.accountmanagement.controller;

import com.management.account.accountmanagement.model.SavingsAccount;
import com.management.account.accountmanagement.repository.SavingsAccountRepository;
import com.management.account.accountmanagement.service.SavingsAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Api(tags = "Savings Account API")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/savings")
public class SavingsAccountController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SavingsAccountController.class);

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private SavingsAccountService savingsAccountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Iterable<SavingsAccount> getAll() {
        return savingsAccountRepository.findAll();
    }

    @ApiOperation(value = "Creates a savings account. Returns savings account object on successful save.")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")
    })
    @RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SavingsAccount addSavingsAccount(@Valid @RequestBody SavingsAccount savingsAccount) throws Exception {
        final SavingsAccount savingsAcc = savingsAccountRepository.save(savingsAccount);
        LOGGER.info("Savings Account has been created successfully.");
        return savingsAcc;
    }

    @ApiOperation(value = "Get the balance of the savings account for the given account number.")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")
    })
    @RequestMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @Transactional
    @ResponseBody
    public Map<String, Object> getBalanceOfSavingsAccount(@PathVariable Long accountNumber) throws Exception {
        return savingsAccountService.getBalanceOfSavingsAccount(accountNumber);
    }

    @ApiOperation(value = "Deletes a savings account for the given account number,subsequent transactions. Returns successful upon completion.")
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "ok"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "An unexpected error occurred")
    })
    @RequestMapping(value = "/{accountNumber}", method = RequestMethod.DELETE)
    @Transactional
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteSavingsAccount(@PathVariable Long accountNumber) throws Exception {
        savingsAccountService.deleteSavingsAccountWithTransaction(accountNumber);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}