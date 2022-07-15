const catchAsync = require("../utils/catchAsync");
const db = require("../models/supportFunctions/dbOperations");

exports.getHome = (req, res) => {
  res.status(200).render("home", {
    title: "Welcome",
  });
};

exports.getOverview = catchAsync(async (req, res) => {
  // 1) Get accounts of the User
  const customerID = req.query.id;
  var sqlStatement = `SELECT * FROM accounts NATURAL JOIN accountholders WHERE customerID = ${customerID}`;
  const accounts = await db.query(sqlStatement);

  sqlStatement = `SELECT * FROM fixeddeposits NATURAL JOIN accountholders WHERE customerID = ${customerID}`;
  const fixedDeposits = await db.query(sqlStatement);

  // 2) Build card Template
  // 3) Display as cards
  res.status(200).render("overview", {
    title: "User Overview",
    accounts: accounts,
    fds: fixedDeposits,
  });
});

exports.accountView = catchAsync(async (req, res) => {
  // 1) Get data
  const accNo = req.query.id;
  const sqlStatement = `SELECT * FROM accounts NATURAL JOIN accountholders WHERE accountNumber = ${accNo}`;
  const result = await db.query(sqlStatement);

  // 2) Build Template

  // 3) Display
  res.status(200).render("account", {
    title: result[0].accountNumber,
    account: result[0],
  });
});
