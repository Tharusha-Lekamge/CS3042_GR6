const db = require("../models/supportFunctions/dbOperations");
//const dbConfig = require("../dbConfig");
const Account = require("../models/accountModel");
const tableName = "accounts";

exports.createAccount = async (req, res) => {
  try {
    const newAccount = new Account(req.body.data);
    const sqlStatement = newAccount.statement;
    const result = await db.query(sqlStatement);

    res.status(200).json({
      status: "Successfully added",
      data: { newAccount },
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

exports.getAllAccounts = async (req, res) => {
  try {
    const sqlStatement = `SELECT * FROM accounts`;
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

/**
 *
 * @param {*} req Pass the request object. Params in the request should be set to AgentID
 * @param {*} res Pass the response object
 *
 * Returns an array containing all accounts assigned to the agent with the passed AgentID
 */
exports.getAllAccByAgentID = async (req, res) => {
  const agentID = req.params.id;
  try {
    const sqlStatement = `SELECT * FROM accounts WHERE agentID = ${agentID}`;
    const result = await db.query(sqlStatement);

    res.json({
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

/**
 * Returns an array containing one element. ELement at index [0] is the requested account
 */
exports.getAccount = async (req, res) => {
  try {
    const accNo = req.params.id;
    const sqlStatement = `SELECT * FROM accounts WHERE accNo = ${accNo}`;
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

exports.updateAccount = async (req, res) => {
  res.status(400).json({
    status: "Failed",
    data: {
      message: "Don't Do this machang",
    },
  });
};

exports.deleteAccount = async (req, res) => {
  try {
    const accNo = req.params.id;
    const sqlStatement = `DELETE FROM ${tableName} WHERE accNo = ${accNo}`;
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
