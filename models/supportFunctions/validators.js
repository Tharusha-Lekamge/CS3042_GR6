const bcrypt = require("bcryptjs");

/* Required input fields are
 * accountNumber
 * date
 * transactionType
 * transactionAmount
 * agentID
 *
 * transactionID is AI
 * transaction charge default is 30.0
 * */
exports.validateTransaction = async function (req, res, next) {
  // Use a validator at the front end too
  console.log("Inside validator");
  if (
    req.body.data.accountNumber === NULL ||
    req.body.data.date === NULL ||
    req.body.data.transactionType === NULL ||
    req.body.data.transactionAMount === NULL ||
    req.body.data.agentID === NULL
  ) {
    console.log("Inside if");
    res.status(400).json({
      status: "Invalid Data",
      data: {
        err,
      },
    });
    next();
  } else {
    next();
  }
};

// Encrypt password middleware
exports.encryptPass = async function (req, res, next) {
  req.body.data.password = await bcrypt.hash(req.body.data.password, 12);
  next();
};

exports.validateCustomer = async function (req, res, next) {
  if (req.body.data.password === req.body.data.confirmPass) {
    next();
  } else {
    res.status(400).json({
      status: "Invalid Data",
      data: {
        err,
      },
    });
  }
};
