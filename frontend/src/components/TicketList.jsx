import React, { useState, useEffect } from 'react';

const TicketList = ({ onSelectTicket }) => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    // Fetch all tickets using the environment variable VITE_API_URL
    const apiUrl = `${import.meta.env.VITE_API_URL}/tickets`;  // Use VITE_API_URL from the environment variable
    fetch(apiUrl)
      .then(response => response.json())
      .then(data => setTickets(data))
      .catch(error => console.error('Error fetching tickets:', error));
  }, []);

  return (
    <div>
      <h3>Tickets</h3>
      <ul>
        {tickets.map(ticket => (
          <li key={ticket.ticketCode} onClick={() => onSelectTicket(ticket)}>
            <p>{ticket.name}</p>
            <p>Price: ${ticket.price}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TicketList;
