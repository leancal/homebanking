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
      clients: [],
      cards: [],
      debitCard: [],
      creditCard: [],
      cardType: [],
      cardColor: [],
      cardType: "",
      cardColor: "",
      button: "",
      cardNumber: "",
      cardsTrue: [],
      number: [],
      debit: [],
      credit: [],
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.clients = response.data;
        this.cards = this.clients.cards;
        console.log(this.cards);
        this.cardsTrue = this.cards.filter((card) => card.active == true);
        this.debit = this.cardsTrue.filter((card) => card.cardType == "DEBIT");
        console.log(this.debit);
        this.credit = this.cardsTrue.filter(
          (card) => card.cardType == "CREDIT"
        );
        console.log(this.credit);
        this.cardNumber = this.cardsTrue.cardNumber;
        console.log(this.cardNumber);
      })
      .catch(function (error) {
        console.log(error);
      });
  },

  methods: {
    newDate(creationDate) {
      return new Date(creationDate).toLocaleDateString("en-US", {
        month: "2-digit",
        year: "2-digit",
      });
    },
    logOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"));
    },
    typeCards() {
      this.cards.forEach((type) => {
        if (type.cardType == "CREDIT") {
          this.creditCard.push(type);
        }
        if (type.cardType == "DEBIT") {
          this.debitCard.push(type);
        }
      });
    },
    newCard() {
      axios
        .post(
          "/api/clients/current/cards",
          `cardColor=${this.cardColor}&cardType=${this.cardType}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => (location.href = "/web/cards.html"))
        .catch((error) => {
          Swal.fire(
            "YOU CAN´T CREATE THIS CARD" + " (" + error.response.data + ")"
          );
        });
    },
    selectValueFirtsColum(id1, id2) {
      let disableSectionColumn1 = document.getElementById(id2);
      let button = document.getElementById(id1);
      disableSectionColumn1.classList.remove("disableSection");
      if (button) {
        button.classList.add("d-none");
      }
    },
    valueButtonType(type) {
      this.cardType = type;
    },
    valueButtonColor(color) {
      this.cardColor = color;
    },
    changeColor(color) {
      let frontCardColor = document.getElementById("frontCard");
      let backCardColor = document.getElementById("backCard");
      switch (color) {
        case "TITANIUM":
          if (
            frontCardColor.classList.contains("card__partGold") &&
            backCardColor.classList.contains("card__partGold")
          ) {
            frontCardColor.classList.remove("card__partGold");
            backCardColor.classList.remove("card__partGold");
          }
          if (
            frontCardColor.classList.contains("card_partSilver") &&
            backCardColor.classList.contains("card_partSilver")
          ) {
            frontCardColor.classList.remove("card_partSilver");
            backCardColor.classList.remove("card_partSilver");
          }
          frontCardColor.classList.add("card_partTitanium");
          backCardColor.classList.add("card_partTitanium");
          break;
        case "SILVER":
          if (
            frontCardColor.classList.contains("card__partGold") &&
            backCardColor.classList.contains("card__partGold")
          ) {
            frontCardColor.classList.remove("card__partGold");
            backCardColor.classList.remove("card__partGold");
          }
          if (
            frontCardColor.classList.contains("card_partTitanium") &&
            backCardColor.classList.contains("card_partTitanium")
          ) {
            frontCardColor.classList.remove("card_partTitanium");
            backCardColor.classList.remove("card_partTitanium");
          }
          frontCardColor.classList.add("card_partSilver");
          backCardColor.classList.add("card_partSilver");
          break;
        case "GOLD":
          if (
            frontCardColor.classList.contains("card_partSilver") &&
            backCardColor.classList.contains("card_partSilver")
          ) {
            frontCardColor.classList.remove("card_partSilver");
            backCardColor.classList.remove("card_partSilver");
          }
          if (
            frontCardColor.classList.contains("card_partTitanium") &&
            backCardColor.classList.contains("card_partTitanium")
          ) {
            frontCardColor.classList.remove("card_partTitanium");
            backCardColor.classList.remove("card_partTitanium");
          }
          frontCardColor.classList.add("card__partGold");
          backCardColor.classList.add("card__partGold");
          break;
        default:
          break;
      }
    },

    addDisableSection(id1, id2, id3) {
      let disableSectionColumn1 = document.getElementById(id2);
      let button1 = document.getElementById(id1);
      let button2 = document.getElementById(id3);
      disableSectionColumn1.classList.add("disableSection");
      if (button1) {
        button1.classList.remove("d-none");
      }
      if (button2) {
        button2.classList.remove("d-none");
      }
    },
  },
}).mount("#app");
