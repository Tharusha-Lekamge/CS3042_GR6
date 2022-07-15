const express = require("express");
const viewController = require("../controllers/viewController");

const router = express.Router();

router.route("/").get(viewController.getHome);
router.route("/account-overview").get(viewController.getOverview);
router.route("/account").get(viewController.accountView);

module.exports = router;
