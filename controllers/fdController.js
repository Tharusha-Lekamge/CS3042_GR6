const db = require("../models/supportFunctions/dbOperations");

const tableName = "fixeddeposits";
const tableCols = `accountNumber, customerID, amount, openingDate, planType, FD_ID`;

exports.createFD = async (req, res) => {
  const { accountNumber, customerID, amount, openingDate, planType, FD_ID } =
    req.body;
  var sqlStatement = `INSERT INTO ${tableName} ${tableCols} VALUES `;
  sqlStatement += `(${accountNumber}, ${customerID}, ${amount}, ${openingDate}, ${planType}, ${FD_ID});`;
};

exports.withdrawFD = async (req, res) => {
  try {
    const { id } = req.params;
    const account = getFdByID(id);
    // Get the FD balance
    const balance = account[0].amount;
    const accNo = account[0].accountNumber;
    // Add the balance to the account Number as a transaction

    // Delete the FD record
    var sqlStatement = `DELETE FROM ${tableName} WHERE FD_ID = ${id}`;
    const deleteRes = await db.query(sqlStatement);

    res.status(200).json({
      status: "Successfully withdrawn",
      data: {
        deleted: deleteRes,
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed",
      data: err,
    });
  }
};

exports.getFD = async (req, res) => {
  try {
    var fdList = [];
    const { id, accNo } = req.query;
    if (!id) {
      const sqlStatement = `SELECT * FROM ${tableName} WHERE accountNumber = ${accNo}`;
      fdList = await db.query(sqlStatement);
    } else {
      const sqlStatement = `SELECT * FROM ${tableName} WHERE FD_ID = ${id}`;
      fdList = await db.query(sqlStatement);
    }

    res.status(200).json({
      status: "Success",
      data: {
        fd: fdList,
      },
    });
  } catch (err) {
    res.status(400).json({
      status: "Failed",
      data: err,
    });
  }
};
