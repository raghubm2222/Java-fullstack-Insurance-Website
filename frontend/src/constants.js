//email validator
function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

//password validator
function isValidPassword(password) {
  return password.length >= 8;
}

//name validator
function isValidName(name) {
  return name.length >= 3;
}

//address validator

function isValidAddress(address) {
  return address.length >= 5;
}

//nominee validator
function isValidNominee(nominee) {
  return nominee.length >= 3;
}

//relationship validator
function isValidRelationship(relationship) {
  return relationship.length >= 3;
}

//phone validator
function isValidPhone(phone) {
  return phone.length >= 10;
}

//logout function
function logout() {
  localStorage.removeItem("user");
  window.location.href = "login.html";
}

//check if the user is logged in
function isLoggedIn() {
  const user = localStorage.getItem("user");
  if (!user) {
    window.location.href = "login.html";
  } 
}

//export all the validators
module.exports = {
  isValidEmail,
  isValidPassword,
  isValidName,
  isValidAddress,
  isValidNominee,
  isValidRelationship,
  isValidPhone,
  logout,
  isLoggedIn,
};
