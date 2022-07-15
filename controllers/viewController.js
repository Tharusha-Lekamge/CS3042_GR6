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
  console.log(customerID);
  res.status(200).render("overview", {
    title: "User Overview",
    accounts: accounts,
    fds: fixedDeposits,
    customerID: customerID,
  });
});

exports.accountView = catchAsync(async (req, res) => {
  // 1) Get data
  const accNo = req.query.id;
  var sqlStatement = `SELECT * FROM accounts NATURAL JOIN accountholders WHERE accountNumber = ${accNo}`;
  const result = await db.query(sqlStatement);

  sqlStatement = `SELECT DISTINCT * FROM transaction WHERE accountNumber = ${accNo} ORDER BY date`;
  const transactions = await db.query(sqlStatement);

  // 2) Build Template

  // 3) Display
  res.status(200).render("account", {
    title: result[0].accountNumber,
    account: result[0],
    transactions: transactions,
  });
});


exports.getLogin = catchAsync(async (req, res) => {
  res.status(200).render("loginForm", {
    title: "Login",
  });
});

exports.getSignup = catchAsync(async (req, res) => {
  res.status(200).render("signup", {
    title: "Sign Up",
  });
});

exports.getReport = catchAsync(async (req, res) => {
  // 1) Get data
  try {
    const { accNo, agentID } = req.query;
    var result = [];

    if (!accNo) {
      const sqlStatement = `SELECT DISTINCT * FROM transaction WHERE agentID = ${agentID} ORDER BY date`;
      result = await db.query(sqlStatement);
    } else {
      const sqlStatement = `SELECT DISTINCT * FROM transaction WHERE accountNumber = ${accNo} ORDER BY date`;
      result = await db.query(sqlStatement);
    }

    res.status(200).render("report", {
      title: "report",
      transactions: result,
      accNo: accNo,
      agentID: agentID,
    });
  } catch (err) {
    console.log(err);
  }
});