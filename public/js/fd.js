const addFD = async (data) => {
  try {
    console.log(data);
    const res = await axios({
      method: "POST",
      url: "/api/v1/fd",
      data: data,
    });

    if (res.data.status === "success") {
      // showAlert("success", "Logged in successfully!");
      alert("Successfully Added FD");
      window.setTimeout(() => {
        location.assign(`/account-overview?id=${data.customerID}`);
      }, 1500);
    }
  } catch (err) {
    alert(err.response.data.message);
    //showAlert("error", err.response.data.message);
  }
};

document.querySelector(".form--fd").addEventListener("submit", (e) => {
  e.preventDefault();
  const accountNumber = document.getElementById("accountNumber").value;
  const customerID = document.getElementById("customerID").value;
  const amount = document.getElementById("amount").value;
  this.dateTime = new Date();
  this.openingDate = this.dateTime.toISOString().slice(0, 19).replace("T", " ");
  const planType = document.getElementById("planType").value;
  const data = {
    accountNumber: accountNumber,
    customerID: customerID,
    amount: amount,
    openingDate: openingDate,
    closingDate: openingDate,
    planType: planType,
  };
  console.log("addFD method");
  addFD(data);
});
