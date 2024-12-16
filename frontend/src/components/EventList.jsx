import React, { useState, useEffect } from 'react';

const EventList = ({ onSelectEvent }) => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Fetch events from the API Gateway
    fetch('http://api-gateway:8083/events')
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
