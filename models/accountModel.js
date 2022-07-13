//const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");
const accountCols = `accountNumber, accountType, agentID, accountBalance`;
const accountHoldersCols = `accountNumber, customerID`;

class Account {
  // Pass req.data to the constructor
  // needed data accNo, customerID, agentID, accType, balance
  constructor(data) {
    this.accNo = data.accNo;
    this.customerID = data.customerID;
    this.agentID = data.agentID;
    this.accType = data.accType;
    this.balance = data.balance;
    // this.interestRate = this.getInterstRate(this.accType);
    // this.minimumAmount = this.getMinimumAmount(this.accType);
    this.statement = this.generateInsertStatement();
  }
  getInterstRate(accType) {
    var sqlStatement = `SELECT interestRate from InterestRates where AccountType = ${this.accType}`;
    const result = db.query(sqlStatement);
    return result[0].interestRate;
  }
  getMinimumAmount(accType) {
    var sqlStatement = `SELECT MinimumAmount from InterestRates where AccountType = ${this.accType}`;
    const result = db.query(sqlStatement);
    return result[0].minimumAmount;
  }

  generateInsertStatement() {
    // Inserting into the accounts table
    var statement = `INSERT INTO accounts ${accountCols} VALUES `;
    statement += `('${this.accNo}', '${this.accType}', '${this.agentID}', '${this.balance});`;
    // Inserting into the accountholders table
    statement += `INSERT INTO accountholders ${accountHoldersCols} VALUES`;
    statement += `('${this.accNo}', '${this.customerID}');`;

    console.log(statement);
    return `${statement}`;
  }
}
