const db = require("../models/supportFunctions/dbOperations");
//const dbConfig = require("../dbConfig");
const Customer = require("../models/customerModel.js");
const validator = require("../models/supportFunctions/validators");

const tableCols =
  "(customerNIC, name, contactNumber, address, birthday, password)";
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

/** Get customer NIC as param in req */
exports.getUser = async (req, res) => {
  try {
    const customerNIC = req.params.ID;
    const sqlStatement = `SELECT * FROM ${tableName} WHERE customerNIC = ${customerNIC}`;
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
    const customerNIC = req.params.ID;
    const sqlStatement = `DELETE FROM ${tableName} WHERE customerNIC = ${customerNIC}`;
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
