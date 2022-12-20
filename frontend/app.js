async function readData() {
  const response = await fetch("http://localhost:8080/get_master_policies");

  const data = await response.json();

  console.log(data);
}

readData();