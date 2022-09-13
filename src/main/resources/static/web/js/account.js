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
let id = urlParams.get("id");

const app = Vue.createApp({
  data() {
    return {
      clients: [],
      accounts: [],
      accountsId: [],
      transaction: [],
      transactionDate: [],
      dateFrom: "",
      dateTo: "",
      accountNumber: "",
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.clients = response.data;
        console.log(this.clients);
        this.accounts = response.data.accounts;
        console.log(this.accounts);

        this.accountsId = this.accounts.find((account) => account.id == id);
        console.log(this.accountsId);

        this.transaction = this.accountsId.transactions;
        console.log(this.transaction);
        this.dateFrom = new Date(this.accountData.creationDate);
        this.dateTo = new Date();
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

    downloadPDF() {
      axios.post('/api/transactions/pdf',
        `dateFrom=${this.dateFrom}&dateTo=${this.dateTo}&accountNumber=${this.accountNumber}`,
        { headers: { "content-type": "application/x-www-form-urlencoded" } }
      )
      .then(response => console.log('pdf downloaded!!!'))
    },

    
  },
}).mount("#app");
