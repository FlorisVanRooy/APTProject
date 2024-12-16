import React, { useState, useEffect } from 'react';

const TicketList = ({ eventId }) => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    if (eventId) {
      // Use the environment variable for API URL
      const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8083'; // Fallback to localhost for local dev

      // Fetch tickets for the selected event from the API Gateway
      fetch(`${apiUrl}/tickets?eventId=${eventId}`)
        .then(response => response.json())
        .then(data => setTickets(data))
        .catch(error => console.error('Error fetching tickets:', error));
    }
  }, [eventId]);

  return (
    <div>
      <h3>Tickets</h3>
      <ul>
        {tickets.map(ticket => (
          <li key={ticket.id}>
            <p>{ticket.name}</p>
            <p>Price: ${ticket.price}</p>
            <button onClick={() => alert(`Ticket ${ticket.name} bought!`)}>Buy Ticket</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TicketList;
