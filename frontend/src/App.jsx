import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import { GoogleLogin, GoogleOAuthProvider } from '@react-oauth/google'; // For OAuth login
import EventList from './components/EventList';
import TicketList from './components/TicketList';

const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('access_token');
    if (token) {
      setIsAuthenticated(true);
    }
  }, []);

  const handleLoginSuccess = (response) => {
    console.log('Login Success:', response);
    const token = response.credential;
    localStorage.setItem('access_token', token);
    setIsAuthenticated(true);
  };

  const handleLoginFailure = (error) => {
    console.log('Login Failure:', error);
  };

  return (
    <GoogleOAuthProvider clientId="1024824062598-27j5e8sg261867hr630sblud6sqoeijm.apps.googleusercontent.com">
      <Router>
        <div>
          <h1>Event Management</h1>

          {!isAuthenticated ? (
            <GoogleLogin onSuccess={handleLoginSuccess} onError={handleLoginFailure} />
          ) : (
            <>
              <nav>
                <ul>
                  <li><Link to="/events">Events</Link></li>
                  <li><Link to="/tickets">Tickets</Link></li>
                </ul>
              </nav>

              <Routes>
                <Route path="/events" element={<EventList />} />
                <Route path="/tickets" element={<TicketList />} />
              </Routes>
            </>
          )}
        </div>
      </Router>
    </GoogleOAuthProvider>
  );
};

export default App;
