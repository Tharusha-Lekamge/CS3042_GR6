package com.example.microbank.Control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.microbank.Control.Exception.AppControllerException;
import com.example.microbank.data.AccountDAO;
import com.example.microbank.data.CustomerDAO;
import com.example.microbank.data.Exception.InvalidAccountException;
import com.example.microbank.data.Model.Account;
import com.example.microbank.data.TransactionDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class AppController_ab implements Serializable {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;
    private Double trCharge= 123.0;
    public static final String AGENT_ID="1234";

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

    public void getDataforAgent(){
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.1.7:3000/api/v1/sync/init/"+AGENT_ID;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String dataString = response.body().string();
                    try {
                        JSONArray json  = new JSONObject(new JSONObject(dataString).getString("data")).getJSONArray("accounts");
                        Log.d("ACCOUNTS", json.toString());

                        //Create 2 methods in CustomerDAO and AccountDAO which takes two JSONArrays as inputs
                        //Get the 2 JSON arrays from the response and pass them to the 2 objects
                        //These methods should iterate through the arrays and add the values to tables
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
