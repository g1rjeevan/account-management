package com.management.account.accountmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "SAVINGS_ACCOUNT",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ACCOUNT_NUMBER", "EMAIL", "PHONE", "AADHAR_ID"}))
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SavingsAccount implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, unique = true)
    private Long accountNumber;

    @Column(name = "FULL_NAME", nullable = false)
    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Email
    @NotBlank(message = "email is mandatory")
    private String email;

    @Column(name = "PHONE", nullable = false, unique = true)
    @NotBlank(message = "phone is mandatory")
    private String phone;

    @Column(name = "AADHAR_ID", nullable = false, unique = true)
    @NotBlank(message = "aadharId is mandatory")
    @Length(max = 10, min = 10)
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String aadharId;

    @Column(name = "BALANCE_AMOUNT", nullable = false)
    @DecimalMin(value = "0.00")
    @JsonProperty(value = "balanceAmount", access = JsonProperty.Access.READ_ONLY, defaultValue = "0.00")
    private BigDecimal balanceAmount = new BigDecimal(0.0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "TRANSACTIONS")
    @JsonIgnore
    private List<Transaction> transactions;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @JsonIgnore
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "UPDATED_DATE")
    @JsonIgnore
    private Date updatedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadharId() {
        return aadharId;
    }

    public void setAadharId(String aadharId) {
        this.aadharId = aadharId;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                ", accountNumber=" + accountNumber +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", aadharId=" + aadharId +
                ", balanceAmount=" + balanceAmount +
                ", transactions=" + transactions +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
