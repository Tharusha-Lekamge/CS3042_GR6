package com.example.microbank.Control;



import android.content.Context;

import com.example.microbank.data.AccountDAO;
import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.Implementation.AccountDAO_Imp;
import com.example.microbank.data.Implementation.CustomerDAO_Imp;
import com.example.microbank.data.Implementation.TransactionDAO_Imp;
import com.example.microbank.data.Model.Account;
import com.example.microbank.data.TransactionDAO;

import java.io.Serializable;
import java.util.List;

public class AppController extends AppController_ab {
    private Context context;


    public AppController(Context context) {
        this.context = context;
        setup();
    }

    public void setup(){
        TransactionDAO transactionDAO = new TransactionDAO_Imp(context);
        AccountDAO accountDAO = new AccountDAO_Imp(context);
        setAccountDAO(accountDAO);
        setTransactionsDAO(transactionDAO);
        CustomerDAO customerDAO = new CustomerDAO_Imp(context);
        setCustomerDAO(customerDAO);

        /*Run this below methods to load dummy data first time to the tables. Then comment out. Or else will run into unique constraint errors*/
//        accountDAO.initAccTable();
//        customerDAO.initCustomerTable();
    }

}
