const express = require("express");
const userController = require("../controllers/userController");
const accountController = require("../controllers/accountController");

const router = express.Router();

router.route("/").get(userController.getAllUsers);
router.route("/:id").get(userController.getAllLoginInfoByAgentID);

module.exports = router;
