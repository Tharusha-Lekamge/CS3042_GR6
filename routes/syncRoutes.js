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
  .route("/:id")
  .get(userController.getUser)
  .patch(userController.updateUser)
  .delete(userController.deleteUser);

module.exports = router;
