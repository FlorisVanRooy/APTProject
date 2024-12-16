import React, { useState, useEffect } from 'react';

const TicketList = ({ event }) => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8083'; // Fallback to localhost for local dev

    // Fetch all tickets from the API Gateway
    fetch(`${apiUrl}/tickets`)
      .then(response => response.json())
      .then(data => setTickets(data))
      .catch(error => console.error('Error fetching tickets:', error));
  }, []);

  const handleTicketClick = (ticket) => {
    // Immediately post a reservation to the reservation-service
    const reservationData = {
      ticketCode: ticket.ticketCode,
    };

    fetch('http://api-gateway:8083/registrations', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(reservationData),
    })    
      .then(response => response.json())
      .then(data => {
        alert(`Successfully reserved ticket: ${ticket.name}`);
      })
      .catch(error => console.error('Error reserving ticket:', error));
  };

  return (
    <div>
      <h3>Tickets</h3>
      <ul>
        {tickets.map(ticket => (
          <li key={ticket.ticketCode} onClick={() => handleTicketClick(ticket)}>
            <p>{ticket.type}</p>
            <p>Price: ${ticket.price}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TicketList;
