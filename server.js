const mysql = require("mysql2");
const dotenv = require("dotenv");
const app = require("./app");
const dbConfig = require("./dbConfig");
dotenv.config({ path: "./config.env" });

const port = process.env.port || 3000;

// Create a mySQL DB connection
// var dbCon = mysql.createConnection(dbConfig);

// Connect to the DB
// dbCon
//   .connect((err) => {
//     if (err) {
//       throw err;
//     }
//   })
//   .then(() => {
//     console.log(`Connected to DB ${hostname} - ${dbName}`);
//   });

app.listen(port, () => {
  console.log(`listening on port ${port}`);
});
