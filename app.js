var express = require("express");
const app = express();
app.use(express.json());

//ROUTERS
const transactionRouter = require("./routes/transactionRoutes");
const userRouter = require("./routes/userRoutes");
const syncRouter = require("./routes/syncRoutes");
const accountRouter = require("./routes/accountRoutes");

app.use("/api/v1/transaction", transactionRouter);
app.use("/api/v1/user", userRouter);
app.use("/api/v1/sync", syncRouter);
app.use("/api/v1/account", accountRouter);

module.exports = app;
