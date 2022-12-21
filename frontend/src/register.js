const user = localStorage.getItem("user");
if (user) {
  window.location.href = "index.html";
}

const registerForm = document.getElementById("registerForm");

const nameErrorTag = document.getElementById("nameerror");
const emailErrorTag = document.getElementById("emailerror");
const passwordErrorTag = document.getElementById("passworderror");
const addressErrorTag = document.getElementById("addresserror");
const nomineeErrorTag = document.getElementById("nomineeerror");
const relationshipErrorTag = document.getElementById("relationshiperror");
const phoneErrorTag = document.getElementById("phoneerror");

registerForm.addEventListener("submit", (event) => {
  event.preventDefault();

  clearErrors();

  const nameInput = registerForm.elements.name;
  const emailInput = registerForm.elements.email;
  const passwordInput = registerForm.elements.password;
  const addressInput = registerForm.elements.address;
  const nomineeInput = registerForm.elements.nominee;
  const relationshipInput = registerForm.elements.relationship;
  const phoneInput = registerForm.elements.phone;

  const data = {
    name: nameInput.value,
    email: emailInput.value,
    password: passwordInput.value,
    address: addressInput.value,
    nominee: nomineeInput.value,
    relationship: relationshipInput.value,
    phone: phoneInput.value,
  };

  const nameError = validateName(data.name);
  const emailError = validateEmail(data.email);
  const passwordError = validatePassword(data.password);
  const addressError = validateAddress(data.address);
  const nomineeError = validateNominee(data.nominee);
  const relationshipError = validateRelationship(data.relationship);
  const phoneError = validatePhone(data.phone);

  if (nameError) nameErrorTag.innerHTML = nameError;
  if (emailError) emailErrorTag.innerHTML = emailError;
  if (passwordError) passwordErrorTag.innerHTML = passwordError;
  if (addressError) addressErrorTag.innerHTML = addressError;
  if (nomineeError) nomineeErrorTag.innerHTML = nomineeError;
  if (relationshipError) relationshipErrorTag.innerHTML = relationshipError;
  if (phoneError) phoneErrorTag.innerHTML = phoneError;

  if (
    nameError ||
    emailError ||
    passwordError ||
    addressError ||
    nomineeError ||
    relationshipError ||
    phoneError
  ) {
    return;
  }

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
        emailErrorTag.innerHTML = data.error;
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

//function to clear errors
function clearErrors() {
  nameErrorTag.innerHTML = "";
  emailErrorTag.innerHTML = "";
  passwordErrorTag.innerHTML = "";
  addressErrorTag.innerHTML = "";
  nomineeErrorTag.innerHTML = "";
  relationshipErrorTag.innerHTML = "";
  phoneErrorTag.innerHTML = "";
}

//email validator
function validateEmail(email) {
  if (!email) return "Email is required";
  const isValid = /\S+@\S+\.\S+/.test(email);
  if (!isValid) return "Please enter a valid email address";
  return null;
}

//password validator
function validatePassword(password) {
  if (!password) return "Password is required";
  if (password.length < 8) return "Password must be at least 8 characters long";
  if (!/\d/.test(password)) return "Password must contain at least one number";
  if (!/[A-Z]/.test(password))
    return "Password must contain at least one uppercase letter";
  if (!/[a-z]/.test(password))
    return "Password must contain at least one lowercase letter";
  return null;
}

//name validator
function validateName(name) {
  if (!name) return "Name is required";
  if (name.length < 3) return "Name must be at least 3 characters long";
  return null;
}

//address validator
function validateAddress(address) {
  if (!address) return "Address is required";
  if (address.length < 10) return "Address must be at least 10 characters long";
  return null;
}

//nominee validator
function validateNominee(nominee) {
  if (!nominee) return "Nominee is required";
  if (nominee.length < 3) return "Nominee must be at least 3 characters long";
  return null;
}

//relationship validator
function validateRelationship(relationship) {
  if (!relationship) return "Relationship is required";
  if (relationship.length < 3)
    return "Relationship must be at least 3 characters long";
  return null;
}

//phone validator
function validatePhone(phone) {
  if (!phone) return "Phone is required";
  if (phone.length !== 10) return "Phone must be at least 10 characters long";
  if (!/^\d+$/.test(phone)) return "Phone must be only numbers";
  return null;
}
