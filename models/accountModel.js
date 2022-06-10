const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");

class Account {
  //Properties
  interestRate;
  minimumAmount;

  //Pass req.data to the constructor
  constructor(data) {
    this.accNo = accNo;
    this.customerNIC = customerNIC;
    this.agentID = agentID;
    this.accType = accType;
    this.balance = balance;
    this.interestRate = this.getInterstRate(this.accType);
    this.minimumAmount = this.getMinimumAmount(this.accType);
  }
  getInterstRate(accType) {
    var sqlStatement = `SELECT interestRate from InterestRates where AccountType = ${this.accType}`;
    const result = await db.query(sqlStatement);
    return result;
  }
  getMinimumAmount(accType) {
    var sqlStatement = `SELECT MinimumAmount from InterestRates where AccountType = ${this.accType}`;
    const result = await db.query(sqlStatement);
    return result;
  }
}
