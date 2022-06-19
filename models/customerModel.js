const mysql = require("mysql2");


// pass req.body.data to the constructor
class Customer {
  constructor(data) {
    this.tableName = "customer";
    this.data = data;

    //this.customerID = data.customerID || "NULL";  // set to AI
    this.password = data.password; // required
    this.confirmPass = data.confirmPass; // required
    this.customerNIC = data.customerNIC; // required
    this.name = data.name; // required
    this.contactNumber = data.contactNumber; // required
    this.address = data.address; // required
    this.birthday = new Date(data.birthday); // required
    this.statement = this.generateInsertStatement();
  }

  generateInsertStatement() {
    const cols =
      "(`password`, `customerNIC`, `name`, `contactNumber`, `address`, `birthday`)";
    var statement = `INSERT INTO ${this.tableName} ${cols} VALUES`;
    var values = `('${this.password}', '${this.customerNIC}', '${this.name}', '${this.contactNumber}', '${this.address}', '${this.birthday}')`;
    return `${statement} ${values}`;
  }
}

module.exports = Customer;