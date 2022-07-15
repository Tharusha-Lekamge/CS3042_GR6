/* eslint-disable */
// import axios from "axios";
// import { showAlert } from "./alerts";

const login = async (customerID, password) => {
  try {
    const res = await axios({
      method: "POST",
      url: "/api/v1/user/login",
      data: {
        customerID,
        password,
      },
    });

    if (res.data.status === "success") {
      // showAlert("success", "Logged in successfully!");
      // window.setTimeout(() => {
      //   location.assign("/");
      // }, 1500);
      console.log("success");
    }
  } catch (err) {
    //showAlert("error", err.response.data.message);
  }
};

const logout = async () => {
  try {
    const res = await axios({
      method: "GET",
      url: "/api/v1/users/logout",
    });
    if ((res.data.status = "success")) location.reload(true);
  } catch (err) {
    console.log(err.response);
    showAlert("error", "Error logging out! Try again.");
  }
};

document.querySelector(".form").addEventListener("submit", (e) => {
  e.preventDefault();
  const customerID = document.getElementById("customerID").value;
  const password = document.getElementById("password").value;
  login(customerID, password);
});
