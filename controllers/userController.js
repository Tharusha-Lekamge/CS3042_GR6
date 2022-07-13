const db = require("../models/supportFunctions/dbOperations");
const Customer = require("../models/customerModel.js");
const validator = require("../models/supportFunctions/validators");
const mysql = require("mysql");
const config = require("../dbConfig");

const catchAsync = require("../utils/catchAsync");
const AppError = require("../utils/appError");

const tableCols =
  "(`customerID`, `password`, `customerNIC`, `firstName`, `lastName`, `contactNumber`, `address`, `birthday`)";
const tableName = "customer";

exports.getAllUsers = async (req, res) => {
  try {
    const sqlStatement = `SELECT * FROM ${tableName}`;
    const result = await db.query(sqlStatement);

    result.forEach(function (el) {
      el.password = "NULL";
    });

    res.status(200).json({
      status: "Success",
      data: {
        customers: result,
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed to get",
      data: {
        err,
      },
    });
  }
};

exports.getAllLoginInfoByAgentID = async (req, res) => {
  try {
    //console.log(req.query);
    const agentID = req.query.id;
    var sqlStatement = `SELECT * FROM accounts NATURAL JOIN accountholders WHERE agentID = ${agentID} ORDER BY customerID`;
    const resultAccounts = await db.query(sqlStatement);

    var userArray = new Array();

    resultAccounts.forEach(async (el) => {
      var cusID = el.customerID;
      sqlStatement = `SELECT * FROM ${tableName} WHERE customerID = ${cusID}`;

      const result = await db.query(sqlStatement);
      userArray.push(result[0]);
    });

    // Just to give some time
    const result1 = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        accounts: resultAccounts[0],
        users: userArray,
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed to get dunno why",
      data: {
        err,
      },
    });
  }
};

/** Get customer NIC as param in req */
exports.getUser = async (req, res) => {
  try {
    const customerID = req.query.id;
    const sqlStatement = `SELECT * FROM ${tableName} WHERE customerID = ${customerID}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        customers: result[0],
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed to get",
      data: {
        err,
      },
    });
  }
};

exports.updateUser = async (req, res) => {
  res.status(400).json({
    status: "Failed",
    data: {
      message: "Ee miss ada wada na",
    },
  });
};

exports.deleteUser = async (req, res) => {
  try {
    const customerID = req.params.id;
    const sqlStatement = `DELETE FROM ${tableName} WHERE customerID = ${customerID}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Deleted Successfully",
      data: {
        customers: result,
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed to Delete",
      data: {
        err,
      },
    });
  }
};

exports.getUserAndAccByID = async (req, res) => {
  try {
    const customerID = req.query.id;
    const sqlStatement = `SELECT customerID, password, customerID FROM ${tableName} WHERE customerID = ${customerID}`;
    const resultUser = await db.query(sqlStatement);

    sqlStatement = `SELECT * FROM accounts WHERE customerID = ${customerID}`;
    const resultAccounts = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        customer: resultUser[0],
        account: resultAccounts[0],
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed to get",
      data: {
        err,
      },
    });
  }
};