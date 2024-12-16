import React, { useState } from 'react';
import EventList from './components/EventList';
import TicketList from './components/TicketList';
import Register from './components/Register';

const App = () => {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [selectedTicket, setSelectedTicket] = useState(null);

  return (
    <div>
      <h1>Event Registration</h1>
      
      {!selectedEvent ? (
        <EventList onSelectEvent={setSelectedEvent} />
      ) : (
        <>
          <h2>{selectedEvent.name}</h2>
          <TicketList eventId={selectedEvent.id} onSelectTicket={setSelectedTicket} />
          {selectedTicket && (
            <Register event={selectedEvent} ticket={selectedTicket} />
          )}
        </>
      )}
    </div>
  );
};

export default App;
