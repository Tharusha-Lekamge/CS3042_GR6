const mysql = require("mysql");
// const db = require("../models/supportFunctions/dbOperations");

/* Required input fields are
 * accountNumber
 * date
 * transactionType
 * transactionAmount
 * agentID
 *
 * transactionID is AI
 * transaction charge default is 30.0
 * */

// Does transaction need an account type?
class Transaction {
  constructor(data) {
    this.tableName = "transaction";
    console.log(data);

    this.transactionID = data.transactionID;
    this.accountNumber = data.accountNumber;
    this.dateTime = new Date(data.date);
    console.log("after date ");
    this.sqlDate = this.dateTime.toISOString().slice(0, 19).replace("T", " ");
    console.log("after date ascasdc");
    this.transactionType = data.transactionType;
    this.transactionAmount = data.transactionAmount;
    this.transactionCharge = data.transactionCharge;
    this.agentID = data.agentID;

    this.statement = this.generateInsertStatement();
  }
  generateInsertStatement() {
    //console.log("inside functuon");
    const cols =
      "(transactionID, accountNumber, date, transactionType, transactionAmount, transactionCharge, agentID)";
    var statement = `INSERT INTO ${this.tableName} ${cols} VALUES`;
    var values = `('${this.transactionID}', '${this.accountNumber}', '${this.sqlDate}', '${this.transactionType}', '${this.transactionAmount}', '${this.transactionCharge}', '${this.agentID}')`;
    const state = statement + " " + values;
    //console.log(state);
    return state;
  }
}

module.exports = Transaction;