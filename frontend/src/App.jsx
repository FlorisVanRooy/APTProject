import React, { useState, useEffect } from 'react';
import { GoogleLogin, GoogleOAuthProvider } from '@react-oauth/google';  // For OAuth login
import EventList from './components/EventList';
import TicketList from './components/TicketList';
import Register from './components/Register';

const App = () => {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // Check if there's an access token stored in localStorage on component mount
  useEffect(() => {
    const token = localStorage.getItem('access_token');
    if (token) {
      setIsAuthenticated(true);
    }
  }, []);

  const handleLoginSuccess = (response) => {
    console.log('Login Success:', response);
    const token = response.credential;
    // Store the access token in localStorage for further use
    localStorage.setItem('access_token', token);
    setIsAuthenticated(true);
  };

  const handleLoginFailure = (error) => {
    console.log('Login Failure:', error);
  };

  const handleSelectEvent = (event) => {
    setSelectedEvent(event);
    setSelectedTicket(null);  // Reset ticket selection when a new event is selected
  };

  const handleSelectTicket = (ticket) => {
    setSelectedTicket(ticket);  // Set the selected ticket
  };

  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
      <div>
        <h1>Event Registration</h1>

        {/* Display Google Login Button if user is not authenticated */}
        {!isAuthenticated ? (
          <GoogleLogin
            onSuccess={handleLoginSuccess}
            onError={handleLoginFailure}
          />
        ) : (
          <>
            {!selectedEvent ? (
              <EventList onSelectEvent={handleSelectEvent} />
            ) : !selectedTicket ? (
              <TicketList onSelectTicket={handleSelectTicket} />
            ) : (
              <Register event={selectedEvent} ticket={selectedTicket} />
            )}
          </>
        )}
      </div>
    </GoogleOAuthProvider>
  );
};

export default App;
