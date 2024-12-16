import React, { useState, useEffect } from 'react';

const TicketList = ({ onSelectTicket }) => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    // Fetch all tickets using the environment variable VITE_API_URL
    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8083'; // Fallback to localhost for local dev
    fetch(apiUrl + '/tickets', {
      method: 'GET',
      credentials: 'include', // Needed if the server uses credentials
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => setTickets(data))
      .catch(error => console.error('Error fetching tickets:', error));
  }, []);

  return (
    <div>
      <h3>Tickets</h3>
      <ul>
        {tickets.map(ticket => (
          <li key={ticket.ticketCode} onClick={() => onSelectTicket(ticket)}>
            <p>{ticket.type}</p>
            <p>Price: ${ticket.price}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TicketList;
