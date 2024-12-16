import React, { useState } from 'react';

const Register = ({ event, ticket }) => {
  const [userDetails, setUserDetails] = useState({ name: '', email: '' });

  const handleRegister = () => {
    // POST registration data to the registration service through API Gateway
    const registrationData = {
      user: userDetails,
      eventId: event.id,
      ticketId: ticket.id,
    };

    // Use the environment variable for the API URL
    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8083'; // Fallback to localhost for local dev

    fetch(`${apiUrl}/registration`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(registrationData),
    })
      .then(response => response.json())
      .then(data => {
        alert('Successfully registered for the event!');
      })
      .catch(error => console.error('Error registering:', error));
  };

  return (
    <div>
      <h2>Register for {event.name}</h2>
      <input
        type="text"
        placeholder="Your Name"
        value={userDetails.name}
        onChange={e => setUserDetails({ ...userDetails, name: e.target.value })}
      />
      <input
        type="email"
        placeholder="Your Email"
        value={userDetails.email}
        onChange={e => setUserDetails({ ...userDetails, email: e.target.value })}
      />
      <button onClick={handleRegister}>Register</button>
    </div>
  );
};

export default Register;
