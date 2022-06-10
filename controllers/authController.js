const Customer = require("../models/customerModel");
const mysql = require("mysql2");
const db = require("../models/supportFunctions/dbOperations");
const bcrypt = require("bcryptjs");

//signup function
exports.signUp = async (req, res, next) => {
  const newCustomer = new Customer(req.body.data);
  res.status(201).json({
    status: "success",
    data: {
      user: newUser,
    },
  });
};
