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

const app = Vue.createApp({
  data() {
    return {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
    };
  },

  created() {},
  methods: {
    login() {
      axios
        .post("/api/login", `email=${this.email}&pwd=${this.password}`, {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((response) =>
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Log in successfully",
            showConfirmButton: false,
            timer: 1500,
          })
          )
        .then(() => {
          if(this.email.includes("@admin.com")){
            window.location.href = "/manager.html";
          }else{
            window.location.href = "./accounts.html";
          }
        })
        .catch((response) =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Wrong email or password! Please try again.",
          })
        );
    },

    logOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"));
    },

    registerForm() {
      axios
        .post(
          "/api/clients",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        
        .then((response) =>
          axios.post("/api/login", `email=${this.email}&pwd=${this.password}`)
        )
        .then((response) =>
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Welcome to our bank!",
            showConfirmButton: false,
            timer: 1500,
          })
          )
        .then((x) => (location.href = "/web/accounts.html"))
        .then((response) => console.log("registered!!!"))
        .catch((response) =>
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "This email is already registered!",
                    })
        );
    },
  },
}).mount("#app");
