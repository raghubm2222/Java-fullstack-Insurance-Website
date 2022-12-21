console.log("Success Page");
const user = localStorage.getItem("tempuser");

if(!user){
    window.location.href = "register.html";
}

const userJson = JSON.parse(user);

const userinfo = document.getElementById("userinfo");

userinfo.innerHTML = `<h3>User Details</h3><p>Customer Id : <strong>${userJson.id}</strong> </p><p>Name : <strong>${userJson.name}</strong></p> <p>Email : <strong>${userJson.email}</strong></p> `;

localStorage.removeItem("tempuser");