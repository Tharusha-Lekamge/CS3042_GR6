const db = require("../models/supportFunctions/dbOperations");
//const dbConfig = require("../dbConfig");

exports.createAccount = async (req, res) => {};

exports.getAllAccounts = async (req, res) => {
  try {
    const sqlStatement = `SELECT * FROM ${tableName}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        accounts: result,
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

exports.getAccount = async (req, res) => {};

exports.updateAccount = async (req, res) => {};

exports.deleteAccount = async (req, res) => {};
