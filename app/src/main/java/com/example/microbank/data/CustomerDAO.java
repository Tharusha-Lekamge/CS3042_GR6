package com.example.microbank.data;

public interface CustomerDAO {
    public Boolean checkUserNamePassword(String username, String password);
    public void initCustomerTable();
}
