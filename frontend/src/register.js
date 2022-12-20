const user = localStorage.getItem("user");
if (user) {
  window.location.href = "index.html";
}

const registerForm = document.getElementById("registerForm");

registerForm.addEventListener("submit", (event) => {
  event.preventDefault();

  const name = registerForm.elements.name.value;
  const email = registerForm.elements.email.value;
  const password = registerForm.elements.password.value;
  const address = registerForm.elements.address.value;
  const nominee = registerForm.elements.nomineename.value;
  const relationship = registerForm.elements.relationship.value;
  const phone = registerForm.elements.contactnumber.value;

  const data = {
    name,
    email,
    password,
    address,
    nominee,
    relationship,
    phone,
  };

  fetch("http://localhost:8080/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.error) {
        const error = document.getElementById("error");
        error.innerHTML = data.error;
        error.style.display = "block";
      } else {
        localStorage.setItem("tempuser", JSON.stringify(data));
        window.location.href = "success.html";
      }
    })
    .catch((error) => {
      console.error(error);
      alert("An error occurred while logging in");
    });
});
