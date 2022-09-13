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
      message: "Hello Vue!",
      clients: [],
      cards: [],
      debit: [],
      credit: [],
      buttonValueFirstColumn: "",
      buttonDebit: "",
      buttonCredit: "",
      cardNumber: "",
      cardsTrue: [],
      number: [],
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.clients = response.data;
        this.cards = this.clients.cards;
        console.log(this.cards);
        
        this.cardsTrue = this.cards.filter( (card) => card.active == true);
        this.debit = this.cardsTrue.filter((card) => card.cardType == "DEBIT");
        console.log(this.debit);
        this.credit = this.cardsTrue.filter((card) => card.cardType == "CREDIT");
        console.log(this.credit);
        this.cardNumber = this.cardsTrue.cardNumber;
        console.log(this.cardNumber);
      })
      .catch(function (error) {
        console.log(error);
      });

    this.dateNow = new Date(Date.now()).toLocaleDateString();
    this.newDate1();
  },

  methods: {
    newDate(creationDate) {
      return new Date(creationDate).toLocaleDateString("en-US", {
        month: "2-digit",
        year: "2-digit",
      });
    },
    newDate1(value) {
      return new Date(value).toLocaleDateString();
    },

    logOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"));
    },

    deleteWarn() {
      swal({
        title: "WARNING INFORMATION",
        text: "If you agree you will no longer see your card information. The final deletion of cards is subject to review by our financial agents.",
        icon: "info",
        buttons: ["Decline", "Accept"],
        dangerMode: true,
      }).then((e) => {
        if (e) {
          this.deleteCard();
        }
      });
    },
  
    deleteCard() {
      console.log("yendo a back");
      axios.patch('/api/clients/current/cards/state', `number=${this.cardNumber}`)
          .then(response => {
              swal({
                  title: "DONE!",
                  text: `Your card is no long available. Fully deletion upon agent report`,
                  icon: "info",
                  buttons: "Ok",
                  dangerMode: false,
              }).then((e) => {
                  if (e) {
                      window.location.href = "./cards.html"
                  }
              })
          }).catch((error) => {
            console.log(error);
            Swal.fire("WE CAN'T DELETE THIS CARD" + " (" + error.response.data + ")")
          });
  },
  }
}).mount("#app");
