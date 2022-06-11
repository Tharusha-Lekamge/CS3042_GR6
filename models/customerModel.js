const mysql = require("mysql2");


// pass req.body.data to the constructor
class Customer {
  constructor(data) {
    this.tableName = "customer";
    this.data = data;

    this.customerID = data.customerID;
    this.password = data.password;
    this.confirmPass = data.confirmPass;
    this.customerNIC = data.customerNIC;
    this.name = data.name;
    this.contactNumber = data.contactNumber;
    this.address = data.address;
    this.birthday = new Date(data.birthday);
    this.statement = generateInsertStatement();
  }

  generateInsertStatement() {
    const cols =
      "(`Password`, `CustomerNIC`, `Name`, `ContactNumber`, `Address`, `Birthday`)";
    var statement = `INSERT INTO ${this.tableName} ${cols} VALUES`;
    var values = `('${this.password}', '${this.customerNIC}', '${this.name}', '${this.contactNumber}', '${this.address}', '${this.birthday}')`;
    return `${statement} ${values}`;
  }
}

module.exports = Customer;