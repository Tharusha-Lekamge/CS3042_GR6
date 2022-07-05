const db = require("../models/supportFunctions/dbOperations");
const Customer = require("../models/customerModel.js");
const validator = require("../models/supportFunctions/validators");

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
  const agentID = req.params.id;
  try {
    var sqlStatement = `SELECT * FROM accounts WHERE agentID = ${agentID} GROUP BY customerNIC`;
    const resultAccounts = await db.query(sqlStatement);

    var userArray = [];

    for (item in resultAccounts) {
      var cusNIC = item.customerNIC;
      sqlStatement = `SELECT customerID, password, customerNIC FROM ${tableName} WHERE customerNIC = ${cusNIC}`;
      var resultAcc = await db.query(sqlStatement);
      userArray.push(resultAcc);
    }

    res.status(200).json({
      status: "Success",
      data: {
        users: userArray,
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

/** Get customer NIC as param in req */
exports.getUser = async (req, res) => {
  try {
    const customerID = req.params.ID;
    const sqlStatement = `SELECT * FROM ${tableName} WHERE customerID = ${customerID}`;
    const result = await db.query(sqlStatement);

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
    const customerID = req.params.ID;
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
