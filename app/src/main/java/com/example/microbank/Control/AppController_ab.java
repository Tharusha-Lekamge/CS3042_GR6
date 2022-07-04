package com.example.microbank.Control;

import com.example.microbank.Control.Exception.AppControllerException;
import com.example.microbank.data.AccountDAO;
import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.Exception.InvalidAccountException;
import com.example.microbank.data.Model.Account;
import com.example.microbank.data.TransactionDAO;

import java.io.Serializable;
import java.util.List;

public abstract class AppController_ab implements Serializable {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;
    private Double trCharge= 123.0;

    public List<Account> getAccounts(String customerID) throws InvalidAccountException {
        return accountDAO.getAccountsList(customerID);
    }

    public void addTransaction(String cusID, String accNo,String type,Double amount,String reference) throws InvalidAccountException {
        if (!(amount==0)){
            accountDAO.updateBalance(accNo,type,trCharge,amount);
            transactionDAO.addTransaction(cusID, accNo,type,trCharge,amount,reference);
        }
    }

    public void addAccount(String accNo,String customerID,String type,Double balance){
        Account account = new Account(accNo,customerID,type,balance);
        accountDAO.addAccount(account);
    }

    public abstract void setup() throws AppControllerException;

    public void setTransactionsDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    public Double getTrCharge() {
        return trCharge;
    }
}
