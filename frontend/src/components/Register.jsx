import React, { useState } from 'react';

const Register = ({ event, ticket }) => {
  const [userDetails, setUserDetails] = useState({
    firstName: '',
    lastName: '',
    email: '',
    quantity: 1,
  });

  const handleRegister = () => {
    // Get the token from localStorage
    const token = localStorage.getItem('access_token');
    
    if (!token) {
      alert('You need to log in first!');
      return;
    }

    // Prepare the registration data
    const reservationData = {
      firstName: userDetails.firstName,
      lastName: userDetails.lastName,
      email: userDetails.email,
      quantity: userDetails.quantity,
      ticketCode: ticket.ticketCode,  // Ensure ticketCode is sent
      eventCode: event.eventCode,  // Ensure eventCode is sent
    };

    // POST request to the API Gateway (which forwards to registration-service)
    fetch(`${import.meta.env.VITE_API_URL}/registrations`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,  // Include the token in the header
      },
      body: JSON.stringify(reservationData),
    })
      .then(response => response.json())
      .then(data => {
        alert('Successfully registered for the event!');
        console.log('Registration response:', data);
      })
      .catch(error => {
        console.error('Error registering:', error);
        alert('Failed to register for the event.');
      });
  };

  return (
    <div>
      <h2>Register for {event.name}</h2>
      <input
        type="text"
        placeholder="First Name"
        value={userDetails.firstName}
        onChange={e => setUserDetails({ ...userDetails, firstName: e.target.value })}
      />
      <input
        type="text"
        placeholder="Last Name"
        value={userDetails.lastName}
        onChange={e => setUserDetails({ ...userDetails, lastName: e.target.value })}
      />
      <input
        type="email"
        placeholder="Email"
        value={userDetails.email}
        onChange={e => setUserDetails({ ...userDetails, email: e.target.value })}
      />
      <input
        type="number"
        placeholder="Amount"
        value={userDetails.quantity}
        onChange={e => setUserDetails({ ...userDetails, quantity: e.target.value })}
      />
      <button onClick={handleRegister}>Register</button>
    </div>
  );
};

export default Register;
