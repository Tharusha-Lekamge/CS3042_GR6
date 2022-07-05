const express = require("express");
const userController = require("../controllers/userController");
const authController = require("../controllers/authController");
const router = express.Router();
const validator = require("../models/supportFunctions/validators");
const accountController = require("../controllers/accountController");

router.route("/monthly-by-agent").get(authController.protect);
router.route("/monthly-by-account").get(authController.protect);

module.exports = router;
