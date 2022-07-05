const express = require("express");
const userController = require("../controllers/userController");
const authController = require("../controllers/authController");
const router = express.Router();
const validator = require("../models/supportFunctions/validators");
const accountController = require("../controllers/accountController");

router
  .route("/init/:id")
  .get(
    accountController.getAllAccByAgentID,
    userController.getAllLoginInfoByAgentID
  );
router
  .route("/:NIC")
  .get(userController.getUser)
  .patch(userController.updateUser)
  .delete(userController.deleteUser);

// When doing a special request to withdraw money from any agent,
// User goes to special request form and enters the userID/ NIC no/ or account No
// Then the account information and login information is sent ot the agent
router
  .route("/special/:id")
  .get(accountController.getAllAccByNIC, userController.getUser);

module.exports = router;
