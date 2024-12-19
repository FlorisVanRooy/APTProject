import React, { useState, useEffect } from 'react';

const Register = () => {
  const [registrations, setRegistrations] = useState([]);
  const [events, setEvents] = useState({});
  const [tickets, setTickets] = useState({});

  useEffect(() => {
    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8083'; // Fallback to localhost for local dev
    const token = localStorage.getItem('access_token');
  
    if (!token) {
      alert('You need to log in first!');
      return;
    }
  
    // Fetch registrations
    fetch(`${apiUrl}/registrations?token=${token}`) // Token is sent as a query parameter
      .then((response) => response.json())
      .then(async (data) => {
        setRegistrations(data);
  
        // Fetch associated event and ticket details
        const eventsMap = {};
        const ticketsMap = {};
  
        for (const registration of data) {
          // Fetch event details if not already fetched
          if (!eventsMap[registration.eventCode]) {
            const eventResponse = await fetch(`${apiUrl}/events/by-code/${registration.eventCode}`);
            const eventData = await eventResponse.json();
            eventsMap[registration.eventCode] = eventData;
          }
  
          // Fetch ticket details if not already fetched
          if (!ticketsMap[registration.ticketCode]) {
            const ticketResponse = await fetch(`${apiUrl}/tickets/by-code/${registration.ticketCode}`);
            const ticketData = await ticketResponse.json();
            ticketsMap[registration.ticketCode] = ticketData;
          }
        }
  
        setEvents(eventsMap);
        setTickets(ticketsMap);
      })
      .catch((error) => console.error('Error fetching registrations:', error));
  }, []);
  

  return (
    <div>
      <h2>Registrations</h2>
      {registrations.length === 0 ? (
        <p>No registrations found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Quantity</th>
              <th>Event Name</th>
              <th>Ticket Type</th>
            </tr>
          </thead>
          <tbody>
            {registrations.map((registration) => (
              <tr key={registration.id}>
                <td>{registration.firstName}</td>
                <td>{registration.lastName}</td>
                <td>{registration.email}</td>
                <td>{registration.quantity}</td>
                <td>{events[registration.eventCode]?.name || 'Loading...'}</td>
                <td>{tickets[registration.ticketCode]?.type || 'Loading...'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default Register;
