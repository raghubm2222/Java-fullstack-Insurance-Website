const myPoliciesDiv = document.getElementById("mypolicies");

async function loadMyPolicies() {
  try {
    const user = localStorage.getItem("user");
    if (!user) {
      window.location.href = "login.html";
      return;
    }
    const userJson = JSON.parse(user);

    const response = await fetch(
      `http://localhost:8080/my_policies?customerid=${userJson.id}`
    );
    const myPolicies = await response.json();
    if (myPolicies.error) {
      alert(data.error);
      return;
    }
    if (myPolicies.length === 0) {
      myPoliciesDiv.innerHTML = "No policies found";
      return;
    }

    let tableHTML =
      "<table><tr><th>Policy ID</th><th>Policy Name</th><th>Sum Assured</th><th>Premium</th><th>Tenure</th><th>Next Due</th></tr>";

    for (const policy of myPolicies) {
      tableHTML += `
      <tr>
          <td>${policy.policyId}</td>
          <td>${policy.policyName}</td>
          <td>${policy.sumAssured}</td>
          <td>${policy.premium}</td>
          <td>${policy.tenure}</td>
          <td>${policy.nextDue}</td>
      </tr>
  `;
    }

    tableHTML += "</table>";
    myPoliciesDiv.innerHTML = tableHTML;
  } catch (error) {
    console.error(error);
    myPoliciesDiv.innerHTML = "No policies found";
  }
}

loadMyPolicies();
