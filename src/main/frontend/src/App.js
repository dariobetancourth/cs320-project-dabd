import React, { useState } from 'react';
import './App.css'; // Ensure you have a CSS file to style your app

function App() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault(); // Prevent the default form submission

    const response = await fetch('/hello/personalized', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        first: firstName, // Use 'first' as defined in the Person class
        last: lastName,   // Use 'last' as defined in the Person class
      }),
    });

    if (response.ok) {
      const text = await response.text();
      setMessage(text); // Set the response message to state
    } else {
      setMessage("Error: Unable to fetch personalized greeting.");
    }
  };

  return (
      <div className="App">
        <h1>Personalized Greeting</h1>
        <form onSubmit={handleSubmit}>
          <input
              type="text"
              placeholder="First Name"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              required
          />
          <input
              type="text"
              placeholder="Last Name"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              required
          />
          <button type="submit">Submit</button>
        </form>
        <p>{message}</p>
      </div>
  );
}

export default App;
