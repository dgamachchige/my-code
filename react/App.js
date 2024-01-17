import React, { useState } from 'react';
import SockJsClient from 'react-stomp';
import PostRequestComponent from './PostRequestComponent';

const SOCKET_URL = 'http://localhost:8082/ws-message';

const App = () => {
  const [message, setMessage] = useState('You server message here.');

  let onConnected = () => {
    console.log("Connected!!")
  }

  let onMessageReceived = (msg) => {
    setMessage(msg.message);
  }

  return (
    <div>
      <SockJsClient
        url={SOCKET_URL}
        topics={['/topic/message']}
        onConnect={onConnected}
        onDisconnect={console.log("Disconnected!")}
        onMessage={msg => onMessageReceived(msg)}
        debug={false}
      />
      <div>{message}</div>
      <PostRequestComponent />
    </div>
  );
}

export default App;