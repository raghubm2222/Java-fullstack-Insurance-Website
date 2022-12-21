const user = localStorage.getItem("user");
if (user) {
  window.location.href = "index.html";
}

const loginForm = document.getElementById("loginForm");
const emailErrorTag = document.getElementById("emailerror");
const passwordErrorTag = document.getElementById("passworderror");


loginForm.addEventListener("submit", (event) => {
  event.preventDefault();

  clearErrors();

  const email = loginForm.elements.email.value;
  const password = loginForm.elements.password.value;

  const emailError = validateEmail(email);
  const passwordError = validatePassword(password);

  if (emailError) emailErrorTag.innerHTML = emailError;
  if (passwordError) passwordErrorTag.innerHTML = passwordError;

  if (emailError || passwordError) return;

  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.error && data.error.toLowerCase().includes("password")) {
        passwordErrorTag.innerText = data.error;
      } else if (data.error) {
        emailErrorTag.innerText = data.error;
      } else {
        localStorage.setItem("user", JSON.stringify(data));
        window.location.href = "index.html";
      }
    })
    .catch((error) => {
      console.error(error);
      alert(error);
    });
});

//email validator
function validateEmail(email) {
  const isValid = /\S+@\S+\.\S+/.test(email);
  if (!isValid) return "Please enter a valid email address";
  return null;
}

//password validator
function validatePassword(password) {
  if (password.length < 8) return "Password must be at least 8 characters long";
  if (!/\d/.test(password)) return "Password must contain at least one number";
  if (!/[A-Z]/.test(password))
    return "Password must contain at least one uppercase letter";
  if (!/[a-z]/.test(password))
    return "Password must contain at least one lowercase letter";
  return null;
}

function clearErrors() {
  emailErrorTag.innerHTML = "";
  passwordErrorTag.innerHTML = "";
}
