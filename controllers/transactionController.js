const db = require("../models/supportFunctions/dbOperations");
const Transaction = require("../models/transactionModel");
const validators = require("../models/supportFunctions/validators");

const catchAsync = require("../utils/catchAsync");
const AppError = require("../utils/appError");

const tableCols =
  "(transactionID, accountNumber, date, transactionType, transactionAmount, transactionCharge, agentID)";
const tableName = "transaction";

exports.validateBody = async (req, res) => {
  // Check if all needed data are present in the request body
};

exports.createTransaction = async (req, res) => {
  try {
    const transactionArray = req.body.data;
    transactionArray.forEach((elem) => {
      const newTransaction = new Transaction(elem);
      var sqlStatement = newTransaction.statement;
      const result = db.query(sqlStatement);
      // update account balance
      newTransaction.checkBalance();
    });
    // const newTransaction = new Transaction(req.body.data);
    // const sqlStatement = newTransaction.statement;
    // const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Successfully added",
      data: {},
    });
  } catch (err) {
    console.log(err);
    res.status(400).json({
      status: "Failed to add",
      data: {
        err,
      },
    });
  }
};

exports.getAllTransactions = async (req, res) => {
  try {
    const sqlStatement = `SELECT * FROM ${tableName}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        transactions: result,
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

/**Get transaction by ID
 * Input ID in req.params
 */
exports.getTransaction = async (req, res) => {
  try {
    const transactionID = req.params.id;
    console.log(transactionID);
    const sqlStatement = `SELECT * FROM ${tableName} WHERE transactionID = ${transactionID}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        transactions: result.affectedRows,
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

// No need to update transaction details
exports.updateTransaction = async (req, res) => {
  res.status(400).json({
    status: "Why are yout trying update a Transaction???",
    data: {
      err,
    },
  });
};

/**
 * 
 * @param {*} req 
 * @param {*} res 
 * 
 * Don't Do this machang without permission
 */
exports.deleteTransaction = async (req, res) => {
  try {
    const transactionID = req.params.id;
    const sqlStatement = `DELETE FROM ${tableName} WHERE transactionID = ${transactionID}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        transactions: result[0],
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

/**
 * Get all tran
 */
exports.getAllTransactionsByAccNo = async (req, res) => {
  try {
    const accNo = req.params.accNo;
    const sqlStatement = `SELECT * FROM ${tableName} WHERE accountNo = ${accNo}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        transactions: result,
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