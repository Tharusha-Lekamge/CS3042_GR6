const express = require("express");
const userController = require("../controllers/transactionController");
const authCOntroller = require("../controllers/authController");
const { route } = require("express/lib/application");
const router = express.Router();

route.post("/signup", authCOntroller.signUp);

router
  .route("/")
  .post(transactionController.createTransaction)
  .get(transactionController.getAllTransactions);
router
  .route("/:id")
  .post(transactionController.createTransaction)
  .get(transactionController.getTransaction)
  .patch(transactionController.updateTransaction)
  .delete(transactionController.deleteTransaction);

module.exports = router;
