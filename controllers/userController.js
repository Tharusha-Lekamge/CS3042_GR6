const db = require("./dbOperations");
//const dbConfig = require("../dbConfig");
const customer = require("../models/customerModel.js");
const Customer = require("../models/customerModel.js");

const tableCols =
  "(CustomerNIC, Name, ContactNumber, Address, Birthday, Password)";
const tableName = "customer";

customer.encryptPass(req, res, next);

exports.createUSer = async (req, res) => {
  try {
    customer.validateData(req, res, next);
    customer.encryptPass(req, res, next);

    const newCustomer = new Customer(req.body.data);
    var sqlStatement = newCustomer.generateInsertStatement;
    const result = await db.query(sqlStatement);
  } catch (err) {
    res.status(400).json({
      status: "Failed to Create user",
      data: {
        err,
      },
    });
  }
};
exports.getAllUsers = async (req, res) => {
  try {
    const sqlStatement = `SELECT * FROM ${tableName}`;
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
exports.getUser = async (req, res) => {};
exports.updateUser = async (req, res) => {};
exports.deleteUser = async (req, res) => {};
