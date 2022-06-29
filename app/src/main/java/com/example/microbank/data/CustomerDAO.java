package com.example.microbank.data;

import com.example.microbank.data.Model.Customer;

public interface CustomerDAO {
    public Customer checkUserNamePassword(String username, String password);
    public void initCustomerTable();
    public Customer getUser(String customer_id);
}
