const express = require("express");
const accountController = require("../controllers/accountController");

const router = express.Router();

router
  .route("/")
  .post(accountController.createAccount)
  .get(accountController.getAllAccounts);
router
  .route("/:id")
  .get(accountController.getAccount)
  .patch(accountController.updateAccount)
  .delete(accountController.deleteAccount);

module.exports = router;
