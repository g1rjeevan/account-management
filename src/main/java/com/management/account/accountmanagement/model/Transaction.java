package com.management.account.accountmanagement.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "TRANSACTION")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "AMOUNT", nullable = false)
    private double amount;

    @Column(name = "AVAILABLE_BALANCE", nullable = false)
    private BigDecimal availableBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SAVINGS_ACCOUNT_ID", nullable = false)
    private SavingsAccount savingsAccount;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }


    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
