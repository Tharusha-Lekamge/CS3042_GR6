const express = require("express");
const viewController = require("../controllers/viewController");
const authController = require("../controllers/authController");

const router = express.Router();

router.route("/").get(viewController.getHome);
router
  .route("/account-overview")
  .get(authController.protect, viewController.getOverview);
router
  .route("/account")
  .get(authController.protect, viewController.accountView);
router.route("/login").get(viewController.getLogin);
router.route("/signup").get(viewController.getSignup);

module.exports = router;
