package com.example.microbank.data;

import com.example.microbank.data.Model.Transaction;

public interface TransactionDAO {
    public void addTransaction(String customerID, String accNo,String type,Double trCharge,Double amount,String reference);
}
