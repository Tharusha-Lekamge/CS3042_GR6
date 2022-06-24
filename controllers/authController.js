const Customer = require("../models/customerModel");
const jwt = require("jsonwebtoken");
const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");
const bcrypt = require("bcryptjs");
//const validator = require("../models/supportFunctions/validators");

//signup function
exports.signUp = async (req, res, next) => {
  const newCustomer = new Customer(req.body.data);

  // Using jwt token
  const token = jwt.sign(
    { id: newCustomer.customerNIC },
    process.env.JWT_SECRET,
    { expiresIn: process.env.JWT_EXPIRES_IN }
  );

  var sqlStatement = newCustomer.statement;
  //console.log(sqlStatement);
  var result = await db.query(sqlStatement);

  // Get the created user from the database
  sqlStatement = `SELECT * FROM customer WHERE CustomerNIC = ${newCustomer.customerNIC}`;
  result = await db.query(sqlStatement);

  // console.log(result);
  res.status(201).json({
    status: "success",
    token: token,
    data: {
      user: result,
    },
  });
};

exports.login = async (req, res, next) => {
  // Uses object destructuring to get the required fields from the passed object
  var { customerNIC, password } = req.body;

  // 1) check if the customerNIC and password is valid
  if (!customerNIC || !password) {
    res.status(400).json({
      status: "fill the details",
      data: {},
    });
    return;
  }
  // 2) check if user exists and if the password matches
  const sqlStatement = `SELECT * FROM CUSTOMER WHERE customerNIC =  ${customerNIC}`;
  var result = await db.query(sqlStatement);
  // Check for errors
  if (!result) {
    res.status(400).json({
      status: "No such user",
      token: token,
    });
  }

  // If a result is found,
  const isValid = bcrypt.compare(password, result[0].password, (err, valid) => {
    if (err) {
      res.status(400).json({
        status: "Error",
        token: token,
      });
    }
    if (!valid) {
      res.status(400).json({
        status: "Wrong Password",
        token: token,
      });
    }
    {
      // 3) pass the JWT to the client
      const token = jwt.sign(
        { id: result.customerNIC },
        process.env.JWT_SECRET,
        {
          expiresIn: process.env.JWT_EXPIRES_IN,
        }
      );
      res.status(200).json({
        status: "Logged in",
        token: token,
      });
    }
  });
};