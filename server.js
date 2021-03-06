const dotenv = require("dotenv");
const app = require("./app");
dotenv.config({ path: "./config.env" });

const port = process.env.port || 3000;

app.listen(port, () => {
  console.log(`listening on port ${port}`);
});
