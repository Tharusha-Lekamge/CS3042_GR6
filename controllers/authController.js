const { promisify } = require("util");
const Customer = require("../models/customerModel");
const jwt = require("jsonwebtoken");
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

const createSendToken = (user, statusCode, req, res) => {
  const token = signToken(user.customerID);

  res.cookie("jwt", token, {
    expires: new Date(
      Date.now() + process.env.JWT_COOKIE_EXPIRES_IN * 24 * 60 * 60 * 1000
    ),
    httpOnly: true,
    secure: req.secure || req.headers["x-forwarded-proto"] === "https",
  });

  // Remove password from output
  user.password = undefined;

  res.status(statusCode).json({
    status: "success",
    token,
    data: {
      user,
    },
  });
};

//signup function
exports.signUp = async (req, res, next) => {
  const newCustomer = new Customer(req.body.data);

  // Using jwt token
  const token = signToken(newCustomer.customerID);

  var sqlStatement = newCustomer.statement;
  //console.log(sqlStatement);
  var result = await db.query(sqlStatement);

  // Get the created user from the database
  sqlStatement = `SELECT * FROM customer WHERE CustomerID = ${newCustomer.customerID}`;
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
  var { customerID, password } = req.body;

  // 1) check if the customerID and password is valid
  if (!customerID || !password) {
    res.status(400).json({
      status: "fill the details",
      data: {},
    });
    return;
  }
  // 2) check if user exists and if the password matches
  const sqlStatement = `SELECT DISTINCT customerID, password FROM customer WHERE customerID =  ${customerID}`;
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
      createSendToken(result[0], 200, req, res);
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
  } else if (req.cookies.jwt) {
    token = req.cookies.jwt;
  }

  if (!token) {
    return next(new AppError("No token given in the header", 401));
  }
  //2) Validate token (Verification step)
  const decoded = await promisify(jwt.verify)(token, process.env.JWT_SECRET);

  //3) Check if user exists
  const customerID = decoded.userID;
  const findUserSQLstatement = `SELECT DISTINCT customerID FROM customer WHERE customerID = ${customerID}`;
  const curUser = await db.query(findUserSQLstatement);

  // If there is no user
  if (!curUser[0]) {
    return next(new AppError("No user with this ID found", 401));
  }
  //4) Check if User changed pass after JWT issued
  //5) Give access to the route
  req.user = curUser[0];
  res.locals.user = curUser[0];
  next();
});

exports.isLoggedIn = async (req, res, next) => {
  if (req.cookies.jwt) {
    try {
      // 1) verify token
      const decoded = await promisify(jwt.verify)(
        req.cookies.jwt,
        process.env.JWT_SECRET
      );

      // 2) Check if user still exists
      const sqlStatement = `SELECT DISTINCT customerID, password FROM customer WHERE customerID =  ${customerID}`;
      var currentUser = await db.query(sqlStatement);
      if (!currentUser[0]) {
        return next();
      }
      currentUser.password = undefined;

      // THERE IS A LOGGED IN USER
      res.locals.user = currentUser[0];
      return next();
    } catch (err) {
      return next();
    }
  }
  next();
};