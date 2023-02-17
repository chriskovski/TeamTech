const form = document.querySelector('form');

form.addEventListener('submit', (event) => {
  event.preventDefault();

  const formData = new FormData(form);

  // convert form data to JSON
  const jsonData = {};
  formData.forEach((value, key) => {
    if (key === 'title') {
      jsonData.title = value;
    } else if (key === 'name') {
      jsonData.name = value;
    } else if (key === 'numberOfTickets') {
      jsonData.numberOfTickets = value;
    }
  });

  // send the data to the server
  fetch('/booking', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(jsonData)
  })
  .then(response => {
    if (response.ok) {
      alert('Booking successful!');
      form.reset();
    } else {
      alert('Error: ' + response.statusText);
    }
  })
  .catch(error => {
    alert('Error: ' + error);
  });
});
