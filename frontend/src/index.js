const user = localStorage.getItem("user");

const username = document.getElementById("username");
const userinfo = document.getElementById("userinfo");

const userJson = JSON.parse(user);

username.innerHTML = `Hello <span class="name">${userJson.name}</span>`;
userinfo.innerHTML = `<p>Customer Id : <strong>${userJson.id}</strong></p><p>Email : <strong>${userJson.email}</strong></p> <p>Phone : <strong>${userJson.phone}</strong> </p>`;
