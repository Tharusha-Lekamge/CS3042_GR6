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
      alert("Logged in");
      window.setTimeout(() => {
        location.assign("/");
      }, 1500);
      
    }
  } catch (err) {
    alert(err.response.data.message);
    //showAlert("error", err.response.data.message);
  }
};

const logout = async () => {
  try {
    console.log("logout called");
    const res = await axios({
      method: "GET",
      url: "/api/v1/user/logout",
    });
    if ((res.data.status = "success")) location.reload(true);
  } catch (err) {
    console.log(err.response);
    //showAlert("error", "Error logging out! Try again.");
  }
};

document.querySelector(".form").addEventListener("submit", (e) => {
  e.preventDefault();
  const customerID = document.getElementById("customerID").value;
  const password = document.getElementById("password").value;
  login(customerID, password);
});

document.querySelector(".nav__el--logout").addEventListener("click", () => {
  console.log("click triggered");
  logout();
});
