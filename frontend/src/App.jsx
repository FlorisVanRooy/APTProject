import React, { useState } from 'react';
import EventList from './components/EventList';
import TicketList from './components/TicketList';
import Register from './components/Register';

const App = () => {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [selectedTicket, setSelectedTicket] = useState(null);

  const handleSelectEvent = (event) => {
    setSelectedEvent(event);
    setSelectedTicket(null);  // Reset ticket selection when a new event is selected
  };

  const handleSelectTicket = (ticket) => {
    setSelectedTicket(ticket);  // Set the selected ticket
  };

  return (
    <div>
      <h1>Event Registration</h1>

      {!selectedEvent ? (
        <EventList onSelectEvent={handleSelectEvent} />
      ) : !selectedTicket ? (
        <TicketList onSelectTicket={handleSelectTicket} />
      ) : (
        <Register event={selectedEvent} ticket={selectedTicket} />
      )}
    </div>
  );
};

export default App;
