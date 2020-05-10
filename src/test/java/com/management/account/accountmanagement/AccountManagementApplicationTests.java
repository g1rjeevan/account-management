package com.management.account.accountmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.account.accountmanagement.model.SavingsAccount;
import com.management.account.accountmanagement.model.Transaction;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {AccountManagementApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagementApplicationTests {


    @Autowired
    private MockMvc mvc;


    @Test
    @Order(1)
    public void createSavingsAccount() throws Exception {
        mvc.perform(post("/api/v1/savings/").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getSavingsAccount())).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Order(2)
    public void depositTransaction() throws Exception {
        mvc.perform(post("/api/v1/transaction/deposit").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(getDepositTransaction())).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void withdrawTransaction() throws Exception {
        for (int i = 0; i < 3; i++) {
            mvc.perform(post("/api/v1/transaction/withdraw").contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(getWithdrawTransaction())).accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(i != 2 ? status().isOk() : status().is4xxClientError());
        }
    }


    @Test
    @Order(4)
    public void getAllSavingsAccount() throws Exception {
        mvc.perform(get("/api/v1/savings/").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Order(5)
    public void getAllTransaction() throws Exception {
        mvc.perform(get("/api/v1/transaction/")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void getBalanceOfSavingsAccount() throws Exception {
        mvc.perform(get("/api/v1/savings/2509215").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void getLatestTenTransaction() throws Exception {
        for (int i = 0; i < 20; i++) {
            getDepositTransaction();
            getWithdrawTransaction();
        }
        mvc.perform(get("/api/v1/transaction/latest/2509215")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(mvcResult -> {
                    MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
                    List<Transaction> transactions = Arrays.asList(new ObjectMapper().readValue(mockHttpServletResponse.getContentAsByteArray(), Transaction[].class));
                    if (transactions.size() <= 10) {
                        System.out.println("Latest transactions less than 10::");
                    }
                })
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    public void deleteSavingsAccount() throws Exception {
        mvc.perform(delete("/api/v1/savings/2509215").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SavingsAccount getSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountNumber(2509215L);
        savingsAccount.setBalanceAmount(new BigDecimal(0.00000));
        savingsAccount.setFullName("Test Name");
        savingsAccount.setId(2509215L);
        savingsAccount.setAadharId("9021021233");
        savingsAccount.setPhone("+91 8999999992");
        savingsAccount.setEmail("gemail@gemailtest.com");
        return savingsAccount;
    }

    private static Map<String, Object> getDepositTransaction() {
        final HashMap map = new HashMap();
        map.put("amount", 100);
        map.put("accountNumber", 2509215L);
        return map;
    }

    private static Map<String, Object> getWithdrawTransaction() {
        final HashMap map = new HashMap();
        map.put("amount", 50);
        map.put("accountNumber", 2509215L);
        return map;
    }


}
