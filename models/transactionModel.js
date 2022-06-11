const mysql = require("mysql");
const db = require("../models/supportFunctions/dbOperations");

// Does transaction need an account type?
class Transaction {
  constructor(data) {
    this.tableName = "transaction";
    console.log(data);

    this.transactionID = data.TransactionID;
    this.accountNumber = data.AccountNumber;
    this.dateTime = new Date(data.Date);
    console.log("after date ");
    this.sqlDate = this.dateTime.toISOString().slice(0, 19).replace("T", " ");
    console.log("after date ascasdc");
    this.transactionType = data.TransactionType;
    this.accountType = data.AccountType;
    this.transactionAmount = data.TransactionAmount;
    this.transactionCharge = data.TransactionCharge;
    this.statement = this.generateInsertStatement();
  }
  generateInsertStatement() {
    //console.log("inside functuon");
    const cols =
      "(TransactionID, AccountNumber, Date, Time, TransactionType, AccountType, TransactionAmount, TransactionCharge)";
    var statement = `INSERT INTO ${this.tableName} ${cols} VALUES`;
    var values = `('${this.transactionID}', '${this.accountNumber}', '${this.sqlDate}', '${this.sqlDate}', '${this.transactionType}', '${this.accountType}', '${this.transactionAmount}', '${this.transactionCharge}')`;
    const state = statement + " " + values;
    //console.log(state);
    return state;
  }
}

// Error with validator
exports.validateTransaction = async function (req, res, next) {
  if (req.body.data.TransactionCharge === 0) {
    // Default charge
    req.body.data.TransactionCharge = 30;
  } else if (req.body.data.Date === NULL) {
    req.body.data.Date = new Date();
  }
  if (
    req.body.data === NULL ||
    req.body.data.AccountNumber === NULL ||
    req.body.data.TransactionAmount === NULL
  ) {
    res.status(400).json({
      status: "Invalid Data",
      data: {
        err,
      },
    });
  }
  next();
};

module.exports = Transaction;