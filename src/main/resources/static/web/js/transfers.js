const body = document.querySelector("body"),
  sidebar = body.querySelector("nav"),
  toggle = body.querySelector(".toggle"),
  searchBtn = body.querySelector(".search-box"),
  modeSwitch = body.querySelector(".toggle-switch"),
  modeText = body.querySelector(".mode-text");//querySelector es una función que permite seleccionar un elemento del DOM por su selector CSS. En este caso, selecciona el elemento body, el elemento nav, el elemento toggle, el elemento searchBox, el elemento modeSwitch y el elemento modeText.

toggle.addEventListener("click", () => {//addEventListener es una función que permite agregar un evento a un elemento del DOM. En este caso, agrega un evento click al elemento toggle.
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
      clients: [],//Se crea un array vacío para almacenar los clientes.
      accounts: [],
      accountsId: [],
      transferType: [],
      otherAccount: "",//Se crea una variable vacía para almacenar el número de cuenta de destino.
      amount: "",
      description: "",
      accountOrigin: "",
      accountDestiny: "",
      amount: 0,
      description: "",
      cuentaOrigen: true,//Se crea una variable booleana para determinar si la cuenta de origen es la cuenta principal o una cuenta secundaria.
      cuentaDestino: false,
      transaction: [],
      accountsTrue: [],
    };
  },

  created() {
    axios
      .get("/api/clients/current")
      .then((response) => {
        this.clients = response.data;//Se almacena en el array clients el cliente que se ha logueado.
        this.accounts = response.data.accounts;
        this.accountsId = this.accounts.find(
          (account) => account.id == urlName
        );
        this.accountsTrue = this.accounts.filter(
          (account) => account.accountState == true
        );
        this.transaction = this.accounts.transactions;
        console.log(this.transaction);
        
      })
      .catch(function (error) {
        console.log(error);
      });
  },

  methods: {
    logOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"));
    },

    
    newTransfer() {
      axios
        .post(
          "/api/transactions",
          `amount=${this.amount}&description=${this.description}&accountOrigin=${this.accountOrigin}&accountDestiny=${this.accountDestiny}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => {
          
          Swal.fire({
            title: "SUCCESFULL TRANSACTION!!!",
            html: 'I will close in <b></b> miliseconds.',
            footer: '<button type="button" class="btn btn-primary" data-bs-dismiss="modal"><a href="./accounts.html">BACK TO DASHBOARD</a></button>',
            timer: 3000,
            timerProgressBar: true,
            didOpen: () => {
              Swal.showLoading()
              const b = Swal.getHtmlContainer().querySelector('b')
              timerInterval = setInterval(() => {
                b.textContent = Swal.getTimerLeft()
              }, 10)
            },
            willClose: () => {
              clearInterval(timerInterval)
            }
            
          }).then((result) => {
            
            if (result.dismiss === Swal.DismissReason.timer) {
              console.log('I was closed by the timer')
            }
          })
          setTimeout(() => { location.href = "/web/transfers.html"}, 3000);
          
          
          console.log("CREATED");
          
        })
        
        
        .catch((error) => {
          console.log(error);
          Swal.fire("WE CAN'T DO THIS TRANSACTION" + " (" + error.response.data + ")")
        });
    },

    cuentaOrigens() {
      this.cuentaOrigen = true;
      this.cuentaDestino = false;
    },
    cuentaDestinos() {
      this.cuentaDestino = true;
      this.cuentaOrigen = false;
    },
  },

  

}).mount("#app");
