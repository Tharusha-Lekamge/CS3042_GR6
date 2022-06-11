const express = require("express");
const userController = require("../controllers/userController");
const authCOntroller = require("../controllers/authController");
const { route } = require("express/lib/application");
const router = express.Router();

route.post("/signup", authCOntroller.signUp);

router
  .route("/")
  .post(userController.createUser)
  .get(userController.getAllTUser);
router
  .route("/:id")
  .post(userController.createUser)
  .get(userController.getUser)
  .patch(userController.updateUser)
  .delete(userController.deleteUser);

module.exports = router;
