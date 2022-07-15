const express = require("express");
const viewController = require("../controllers/viewController");

const router = express.Router();

router.route("/").get(viewController.getHome);
router.route("/account-overview").get(viewController.getOverview);
router.route("/account").get(viewController.accountView);
router.route("/login").get(viewController.getLogin);
router.route("/signup").get(viewController.getSignup);

module.exports = router;
