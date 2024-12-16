import React, { useState, useEffect } from 'react';

const EventList = ({ onSelectEvent }) => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Use the environment variable for API URL
    const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8083'; // Fallback to localhost for local dev

    // Fetch events from the API Gateway
    fetch(`${apiUrl}/events`)
      .then(response => response.json())
      .then(data => setEvents(data))
      .catch(error => console.error('Error fetching events:', error));
  }, []);

  return (
    <div>
      <h2>Events</h2>
      <ul>
        {events.map(event => (
          <li key={event.id} onClick={() => onSelectEvent(event)}>
            <h3>{event.name}</h3>
            <p>{event.description}</p>
            <p>{event.date}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EventList;
