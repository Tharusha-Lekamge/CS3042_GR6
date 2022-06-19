const Customer = require("../models/customerModel");
const jwt = require("jsonwebtoken");
const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");
const validator = require("../models/supportFunctions/validators");

//signup function
exports.signUp = async (req, res, next) => {
  //validator.encryptPass(req, res, next);
  // Assumes that all data is present
  const newCustomer = new Customer(req.body.data);

  var sqlStatement = newCustomer.statement;
  //console.log(sqlStatement);
  var result = await db.query(sqlStatement);

  // Get the created user from the database
  sqlStatement = `SELECT * FROM customer WHERE CustomerNIC = ${newCustomer.customerNIC}`;
  result = await db.query(sqlStatement);

  // console.log(result);
  res.status(201).json({
    status: "success",
    data: {
      user: result,
    },
  });
};
