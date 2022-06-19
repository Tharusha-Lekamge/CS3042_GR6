var express = require("express");
const app = express();
app.use(express.json());

//ROUTERS
const transactionRouter = require("./routes/transactionRoutes");
const userRouter = require("./routes/userRoutes");

app.use("/api/v1/transaction", transactionRouter);
app.use("/api/v1/user", userRouter);

module.exports = app;
