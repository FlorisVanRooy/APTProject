import React, { useState } from 'react';

const Register = ({ event, ticket }) => {
  const [userDetails, setUserDetails] = useState({
    firstName: '',
    lastName: '',
    email: '',
    ticketAmount: 1, // default to 1
  });

  const handleRegister = () => {
    // Prepare the registration data
    const registrationData = {
      user: {
        firstName: userDetails.firstName,
        lastName: userDetails.lastName,
        email: userDetails.email,
      },
      eventCode: event.eventCode,
      ticketCode: ticket.ticketCode,
      ticketAmount: userDetails.ticketAmount,
    };

    // Use VITE_API_URL for the POST request to the API Gateway
    const apiUrl = `${import.meta.env.VITE_API_URL}/reservations`;  // Use VITE_API_URL from the environment variable

    fetch(apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(registrationData),
    })
      .then(response => response.json())
      .then(data => {
        alert('Successfully registered for the event!');
      })
      .catch(error => {
        console.error('Error registering:', error);
        alert('An error occurred while registering.');
      });
  };

  return (
    <div>
      <h2>Register for {event.name}</h2>
      <p>Ticket: {ticket.name}</p>
      <p>Price: ${ticket.price}</p>

      <label>
        First Name:
        <input
          type="text"
          value={userDetails.firstName}
          onChange={e => setUserDetails({ ...userDetails, firstName: e.target.value })}
        />
      </label>
      <label>
        Last Name:
        <input
          type="text"
          value={userDetails.lastName}
          onChange={e => setUserDetails({ ...userDetails, lastName: e.target.value })}
        />
      </label>
      <label>
        Email:
        <input
          type="email"
          value={userDetails.email}
          onChange={e => setUserDetails({ ...userDetails, email: e.target.value })}
        />
      </label>
      <label>
        Number of Tickets:
        <input
          type="number"
          value={userDetails.ticketAmount}
          onChange={e => setUserDetails({ ...userDetails, ticketAmount: e.target.value })}
          min="1"
        />
      </label>
      <button onClick={handleRegister}>Register</button>
    </div>
  );
};

export default Register;
