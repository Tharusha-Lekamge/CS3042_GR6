const db = require("../models/supportFunctions/dbOperations");
const Transaction = require("../models/transactionModel");
const validators = require("../models/supportFunctions/validators");

const tableCols =
  "(TransactionID, AccountNumber, Date, Time, TransactionType, AccountType, TransactionAmount, TransactionCharge)";
const tableName = "transaction";

exports.validateBody = async (req, res) => {
  // Check if all needed data are present in the request body
};

exports.createTransaction = async (req, res) => {
  try {
    //console.log(req.body.data);
    //const validate = validators.validateTransaction(req, res, next);
    //console.log("validated");
    const newTransaction = new Transaction(req.body.data);
    //console.log(newTransaction);
    const sqlStatement = newTransaction.statement;
    //console.log(sqlStatement);
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Successfully added",
      data: { newTransaction },
    });
  } catch (err) {
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

exports.deleteTransaction = async (req, res) => {
  try{
    const transactionID = req.params.id;
    const sqlStatement = `DELETE FROM ${tableName} WHERE transactionID = ${transactionID}`;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Success",
      data: {
        transactions: result,
      },
    });
  }catch(err){
    res.status(400).json({
      status: "Failed to get",
      data: {
        err,
      },
    });
  }
};
