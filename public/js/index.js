/* eslint-disable */
import { login } from "./login";

document.querySelector(".form").addEventListener("submit", (e) => {
  e.preventDefault();
  const customerID = document.getElementById("customerID").value;
  const password = document.getElementById("password").value;
  login(customerID, password);
});
