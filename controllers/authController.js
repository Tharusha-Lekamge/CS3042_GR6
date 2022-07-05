const { promisify } = require("utils");
const Customer = require("../models/customerModel");
const jwt = require("jsonwebtoken");
const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");
const bcrypt = require("bcryptjs");

const catchAsync = require("../utils/catchAsync");
const AppError = require("../utils/appError");
//const validator = require("../models/supportFunctions/validators");

const signToken = (userID) => {
  return jwt.sign({ userID }, process.env.JWT_SECRET, {
    expiresIn: process.env.JWT_EXPIRES_IN,
  });
};

//signup function
exports.signUp = async (req, res, next) => {
  const newCustomer = new Customer(req.body.data);

  // Using jwt token
  const token = signToken(newCustomer.customerNIC);

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
    });
    return;
  }

  // If a result is found,
  const isValid = bcrypt.compare(password, result[0].password, (err, valid) => {
    if (err) {
      res.status(400).json({
        status: "Error",
      });
      return;
    }
    if (!valid) {
      res.status(400).json({
        status: "Wrong Password",
      });
      return;
    }
    {
      // 3) pass the JWT to the client
      const token = signToken(result[0].customerNIC);
      res.status(200).json({
        status: "Logged in",
        token: token,
      });
    }
  });
};

exports.protect = catchAsync(async (req, res, next) => {
  //1) Get token if exists
  // Token is normally in the header
  var token;
  if (
    req.headers.authorization &&
    req.headers.authorization.startsWith("Bearer")
  ) {
    token = req.headers.authorization.split(" ")[1];
  }
  if (!token) {
    return next(new AppError("No token given in the header", 401));
  }
  //2) Validate token (Verification step)
  const decoded = await promisify(jwt.verify)(token, process.env.JWT_SECRET);
  //3) Check if user exists
  //4) Check if User changed pass after JWT issued
  //5) Give access to the route

  next();
});
