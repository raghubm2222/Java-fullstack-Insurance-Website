const user = localStorage.getItem("user");

const username = document.getElementById("username");
const userinfo = document.getElementById("userinfo");

const userJson = JSON.parse(user);

console.log(userJson);

username.innerHTML = `Hello <span class="name">${userJson.name}</span>`;
userinfo.innerHTML = `<p>Email : <strong>${userJson.email}</strong></p> <p>Phone : <strong>${userJson.phone}</strong> </p>`;
