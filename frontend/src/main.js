const user1 = localStorage.getItem("user");
if (!user1) {
  window.location.href = "login.html";
}

const logout = document.getElementById("logout");

logout.addEventListener("click", (event) => {
  event.preventDefault();
  localStorage.clear();
  window.location.href = "index.html";
});
