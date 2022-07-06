package com.example.microbank.data;

import com.example.microbank.data.Model.Customer;

import org.json.JSONArray;

public interface CustomerDAO {
    public Customer checkUserNamePassword(String username, String password);
    public void initCustomerTable();
    public Customer getUser(String customer_id);
    public boolean isSpecialCustomer(String customerID);
    public void LoadCustomerData(JSONArray customers);
}
