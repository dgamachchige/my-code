import React, { useState } from 'react';

const PostRequestComponent = () => {
  const [jsonInput, setJsonInput] = useState('');
  
  const handleJsonInputChange = (event) => {
    setJsonInput(event.target.value);
  };

  const handlePostRequest = () => {
    // Assuming your REST API is running at http://localhost:8082/message
    const apiUrl = 'http://localhost:8082/message';

    // Make sure to handle errors appropriately in a real application
    fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonInput,
    })
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
        // Handle the response data as needed
      })
      .catch(error => {
        console.error('Error:', error);
        // Handle errors
      });
  };

  return (
    <div>
      <textarea
        rows="5"
        cols="40"
        value={jsonInput}
        onChange={handleJsonInputChange}
        placeholder="Enter JSON input here"
      />
      <br />
      <button onClick={handlePostRequest}>Send POST Request</button>
    </div>
  );
};

export default PostRequestComponent;
