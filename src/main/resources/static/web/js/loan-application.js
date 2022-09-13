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

let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get("id");

const app = Vue.createApp({
  data() {
    return {
      client: [],
      accounts: [],
      payments: [],
      loans: [],
      payment: "",
      loanType: "",
      loanPays: "",
      amount: "",
      accountDestiny: "",
      loanId: 0,
      accountsTrue: [],
    };
  },
  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.allData(response);
      })
      .catch(function (error) {
        console.log(error);
      });

    axios
      .get("/api/loans")
      .then((response) => {
        this.getLoans(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  },

  methods: {
    allData(response) {
      this.client = response.data;
      this.accounts = this.client.accounts;
      this.accountsTrue = this.accounts.filter(
        (account) => account.accountState == true
      );
    },
    getLoans(response) {
      this.loans = response.data;
    },
    newDate(creationDate) {
      return new Date(creationDate).toLocaleDateString("es-AR", {
        month: "2-digit",
        year: "2-digit",
      });
    },
    logOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"));
    },

    loanPetition() {
      axios
        .post("/api/loans", {
          id: `${this.loanId}`,
          amount: `${this.amount}`,
          payment: `${this.loanPays}`,
          numberOriginAccount: `${this.accountDestiny}`,
        })
        .then((response) => {
          Swal.fire({
            title: "CONGRATS, ENJOY YOUR NEW LOAN!!!",
            html: "I will close in <b></b> miliseconds.",
            footer:
              '<button type="button" class="btn btn-primary" data-bs-dismiss="modal"><a href="./accounts.html">BACK TO DASHBOARD</a></button>',
            timer: 3000,
            timerProgressBar: true,
            didOpen: () => {
              Swal.showLoading();
              const b = Swal.getHtmlContainer().querySelector("b");
              timerInterval = setInterval(() => {
                b.textContent = Swal.getTimerLeft();
              }, 10);
            },
            willClose: () => {
              clearInterval(timerInterval);
            },
          }).then((result) => {
            if (result.dismiss === Swal.DismissReason.timer) {
              console.log("I was closed by the timer");
            }
          });
          setTimeout(() => {
            location.href = "/web/loan-application.html";
          }, 3000);

          console.log("CREATED");
        })
        .catch((error) => {
          Swal.fire(
            "YOU CAN´T TAKE MORE LOAN" + " (" + error.response.data + ")"
          );
          console.log(error);
        });
    },
    
  },
}).mount("#app");
