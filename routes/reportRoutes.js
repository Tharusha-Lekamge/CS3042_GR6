const express = require("express");
const authController = require("../controllers/authController");
const transactionController = require("../controllers/transactionController");

const router = express.Router();

router
  .route("/monthly-by-agent")
  .get(transactionController.getAllTransactionsByAccAgent);
router
  .route("/monthly-by-account")
  .get(transactionController.getAllTransactionsByAccAgent);

module.exports = router;
