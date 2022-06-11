const mysql = require("mysql2");
const bcrypt = require("bcryptjs");

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
  }

  generateInsertStatement() {
    const cols =
      "(`Password`, `CustomerNIC`, `Name`, `ContactNumber`, `Address`, `Birthday`)";
    var statement = `INSERT INTO ${this.tableName} ${cols} VALUES`;
    var values = `('${this.password}', '${this.customerNIC}', '${this.name}', '${this.contactNumber}', '${this.address}', '${this.birthday}')`;
    return `${statement} ${values}`;
  }
}

// Encrypt password middleware
exports.encryptPass = async function (req, res, next) {
  req.body.data.password = await bcrypt.hash(req.body.data.password, 12);
  next();
};

exports.validateData = async function (req, res, next) {
  if (req.body.data.password === req.body.data.confirmPass) {
    next();
  } else {
    res.status(400).json({
      status: "Invalid Data",
      data: {
        err,
      },
    });
  }
};

module.exports = Customer;