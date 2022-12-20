const user = localStorage.getItem("user");
if (user) {
  window.location.href = "index.html";
}

// JavaScript to handle form submission and send HTTP request

const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", (event) => {
  event.preventDefault();

  const email = loginForm.elements.email.value;
  const password = loginForm.elements.password.value;

  // //Send HTTP request to server
  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.error) {
        const error = document.getElementById("error");
        error.innerHTML = data.error;
        error.style.display = "block";
      } else {
        localStorage.setItem("user", JSON.stringify(data));
        window.location.href = "index.html";
      }
    })
    .catch((error) => {
      console.error(error);
      alert("An error occurred while logging in");
    });
});
