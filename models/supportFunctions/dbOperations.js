const mysql = require("mysql2/promise");
const config = require("../../dbConfig");

exports.query = async (sql, params) => {
  const connection = await mysql.createConnection(config.db);
  // Use pool if needed to inncrease performance.
  // But saves the DB connection
  // const pool = mysql.createPool(config.db);
  const [results] = await connection.execute(sql, params);
  return results;
};
