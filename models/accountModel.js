//const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");

class Account {
  //Properties
  interestRate;
  minimumAmount;

  //Pass req.data to the constructor
  constructor(data) {
    this.accNo = data.accNo;
    this.customerNIC = data.customerNIC;
    this.agentID = data.agentID;
    this.accType = data.accType;
    this.balance = data.balance;
    this.interestRate = this.getInterstRate(this.accType);
    this.minimumAmount = this.getMinimumAmount(this.accType);
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
    const cols =
      "(`password`, `customerNIC`, `name`, `contactNumber`, `address`, `birthday`)";
    var statement = `INSERT INTO ${this.tableName} ${cols} VALUES`;
    var values = `('${this.password}', '${this.customerNIC}', '${this.name}', '${this.contactNumber}', '${this.address}', '${this.birthday}')`;
    return `${statement} ${values}`;
  }
}
