const body = document.querySelector("body"),
  sidebar = body.querySelector("nav"),
  toggle = body.querySelector(".toggle"),
  searchBtn = body.querySelector(".search-box"),
  modeSwitch = body.querySelector(".toggle-switch"),
  modeText = body.querySelector(".mode-text");

toggle.addEventListener("click", () => {
  sidebar.classList.toggle("close");
});

searchBtn.addEventListener("click", () => {
  sidebar.classList.remove("close");
});

modeSwitch.addEventListener("click", () => {
  body.classList.toggle("dark");

  if (body.classList.contains("dark")) {
    modeText.innerText = "Light mode";
  } else {
    modeText.innerText = "Dark mode";
  }
});

date = new Date();
year = date.getFullYear();
month = date.getMonth() + 1;
day = date.getDate();
document.getElementById("current_date").innerHTML =
  month + "/" + day + "/" + year;

const urlParams = new URLSearchParams(window.location.search);
var myParam = urlParams.get("id");

const app = Vue.createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
      totalAccountsBalance: 0,
      totalLoansBalance: 0,
      totalBalance: 0,
      accountsBalancePercentage: 0,
      paymentsBalancePercentage: 0,
      accountType: 0,
      types: "",
      number: [],
      numberAcc: 0,
      accountNumber: "",
      accountCancel: 0,
      accountsTrue: [],
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.clients = response.data;
        console.log(this.clients);
        this.accounts = response.data.accounts;
        this.loans = response.data.loans;
        console.log(this.loans);
        this.accountsTrue = this.accounts.filter(
          (account) => account.accountState == true
        );
        this.accountNumber = response.data.account.number;
        console.log(this.number);
        console.log(this.accountNumber);
        console.log(this.accountsTrue);
      })
      .catch(function (error) {
        console.log(error);
      });
  },

  methods: {
    newDate(creationDate) {
      return new Date(creationDate).toLocaleDateString();
    },

    logOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"));
    },

    createAccount() {
      axios
        .post(
          "/api/clients/current/accounts",
          `accountType=${this.accountType}`
        )
        .then((response) =>
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Account created successfully",
            showConfirmButton: false,
            timer: 1500,
          })
        )
        .then((x) => window.location.reload())
        .catch((response) =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "You can't create this account!",
          })
        );
    },

    selectAccount() {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Account selected successfully",
        showConfirmButton: false,
        timer: 1500,
      });
    },

    selectAccountType(value) {
      this.accountType = value;
      // Swal.fire({
      //   position: 'center',
      //   icon: 'success',
      //   tittle: value + " " + "account selected",
      //   showConfirmButton: false,
      //   timer:1500
      // })
    },

    deleteAccount() {
      axios
        .patch(
          "/api/clients/current/accounts/accountState",
          `number=${this.accountNumber}`
        )
        .then((response) => {
          console.log(response);
          swal({
            title: "DONE!",
            text: `Your account is no longer available. Fully deletion upon agent report`,
            icon: "info",
            buttons: "Ok",
            dangerMode: false,
          }).then((e) => {
            if (e) {
              console.log("HOLA");
              window.location.href = "./accounts.html";
            }
          });
        })
        .catch((error) => {
          console.log(error);
          Swal.fire("WE CAN'T DELETE THIS ACCOUNT" + " (" + error.response.data + ")")
        });
    },

    deleteWarn() {
      swal({
        title: "WARNING INFORMATION",
        text: "If you agree you will no longer see your account information. The final deletion of transactions and account is subject to review by our financial agents.",
        icon: "info",
        buttons: ["Decline", "Accept"],
        dangerMode: true,
      }).then((e) => {
        if (e) {
          this.deleteAccount();
        }
      });
    },
    
    balancePercentages() {
      this.totalBalance = this.totalAccountsBalance + this.totalLoansBalance;
      this.accountsBalancePercentage =
        (this.totalAccountsBalance * 100) / this.totalBalance;
      this.paymentsBalancePercentage =
        (this.totalLoansBalance * 100) / this.totalBalance;
      this.accountsBalancePercentage = Math.round(
        this.accountsBalancePercentage
      );
      this.paymentsBalancePercentage = Math.round(
        this.paymentsBalancePercentage
      );
    },

    balanceAccounts() {
      this.accounts.forEach((account) => {
        this.totalAccountsBalance += account.balance;
      });
      this.loans.forEach((loan) => {
        this.totalLoansBalance += loan.amount;
      });
    },
  },
}).mount("#app");
