const policySelect = document.getElementById("policySelect");
const policyinformation = document.getElementById("policyinformation");
let buyNowButton;

let selectedPolicy = null;

async function getMaterPolicyById(id) {
  try {
    const response = await fetch(
      `http://localhost:8080/get_master_policy?id=${id}`
    );
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(error);
    alert("An error occurred while loading policy");
  }
}

async function buyPolicy() {
  try {
    const user = localStorage.getItem("user");
    if (!user) {
      window.location.href = "login.html";
      return;
    }
    const userJson = JSON.parse(user);
    const response = await fetch(
      `http://localhost:8080/add_policy?customerid=${userJson.id}&masterpolicyid=${selectedPolicy.id}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    policyinformation.innerHTML = "";
    alert("Policy bought successfully");
  } catch (error) {
    console.error(error);
    alert(error);
  }
}

async function loadPolicyInformation() {
  try {
    const response = await fetch("http://localhost:8080/get_master_policies");
    const masterPolicies = await response.json();
    if (masterPolicies.error) {
      alert(masterPolicies.error);
      return;
    }
    if (masterPolicies.length === 0) {
      policyinformation.innerHTML = "No policies found";
      return;
    }
    masterPolicies.forEach((policy) => {
      const option = document.createElement("option");
      option.value = policy.id;
      option.text = policy.name;
      policySelect.add(option);
    });
    addListner();
  } catch (error) {
    console.error(error);
    alert("An error occurred while loading policy");
  }
}

function addListner() {
  policySelect.addEventListener("change", async (event) => {
    event.preventDefault();
    const selectedPolicyId = event.target.value;
    if (!selectedPolicyId) {
      policyinformation.innerHTML = "";
      return;
    }
    const policy = await getMaterPolicyById(selectedPolicyId);

    selectedPolicy = policy;
    policyinformation.innerHTML = `
      <div class="insurenceinfo" >
      <p>Sum Assured : <strong>₹${selectedPolicy.sumAssured}/-</strong></p>
      <p>Premium : <strong>₹${selectedPolicy.premium}/-</strong></p>
      <p>Tenure : <strong> ${selectedPolicy.tenure} Years</strong></p>
      </div>
      <button id="buynow">Buy Now</button>
    `;
    addListnertoButton();
  });
}

function addListnertoButton() {
  buyNowButton = document.getElementById("buynow");
  buyNowButton.addEventListener("click", (event) => {
    event.preventDefault();
    buyPolicy();
  });
}


loadPolicyInformation();
