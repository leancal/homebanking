let urlParams = new URLSearchParams(window.location.search);
let id = urlParams.get("id");

const app = Vue.createApp({
  data() {
    return {
      message: "Hello Vue!",
      clients: [],
      nombre: "",
      apellido: "",
      email: "",
      clientsId: "",
      name: "",
      amount: "",
      payments: "",
    };
  },

  created() {
    axios
      .get("/api/clients")
      .then((response) => {
        this.clients = response.data;
        console.log(this.clients);
        //client id 
        // this.clientsId  = this.clients.map(client => client.id);
        // console.log(this.clientsId);
      })
      .catch(function (error) {
        console.log(error);
      });
  },

  methods: {
    addClient() {
      if (this.nombre != "" && this.apellido != "" && this.email != "") {
        axios
          .post("/rest/clients", {
            nombre: this.nombre,
            apellido: this.apellido,
            email: this.email,
          })

          .catch(function (error) {
            console.log(error);
          });
      }
    },
    addLoan() {
      axios
        .post('/api/admin/loans', 
          `name=${this.name}&amount=${this.amount}&payments=${this.payments}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => {
          console.log(response);
        })
        // .then((response) => {
        //   window.location.href = "web/manager.html";
        // })
        .catch((error) => {
          Swal.fire(
            "YOU CAN´T TAKE MORE LOAN" + " (" + error.response.data + ")"
          );
          console.log(error);
        });
    }
  },
}).mount("#app");
