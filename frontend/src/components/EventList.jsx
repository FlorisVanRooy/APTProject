import React, { useState, useEffect } from 'react';

const EventList = ({ onSelectEvent }) => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Fetch events using the environment variable VITE_API_URL
    const apiUrl = `${import.meta.env.VITE_API_URL}/events`;  // Use VITE_API_URL from the environment variable
    fetch(apiUrl)
      .then(response => response.json())
      .then(data => setEvents(data))
      .catch(error => console.error('Error fetching events:', error));
  }, []);

  return (
    <div>
      <h2>Events</h2>
      <ul>
        {events.map(event => (
          <li key={event.eventCode} onClick={() => onSelectEvent(event)}>
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
