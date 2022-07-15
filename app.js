var express = require("express");
const app = express();
app.use(express.json());

//ROUTERS
const AppError = require("./utils/appError");
const globalErrorHandler = require("./controllers/errorController");
const transactionRouter = require("./routes/transactionRoutes");
const userRouter = require("./routes/userRoutes");
const syncRouter = require("./routes/syncRoutes");
const accountRouter = require("./routes/accountRoutes");
const fdRouter = require("./routes/fdRoutes");
const reportRouter = require("./routes/reportRoutes");

app.use("/api/v1/transaction", transactionRouter);
app.use("/api/v1/user", userRouter);
app.use("/api/v1/sync", syncRouter);
app.use("/api/v1/account", accountRouter);
app.use("/api/v1/fd", fdRouter);
app.use("/api/v1/report", reportRouter);

app.all("*", (req, res, next) => {
  next(new AppError(`Can't find ${req.originalUrl} on this server!`, 404));
});

app.use(globalErrorHandler);

module.exports = app;
