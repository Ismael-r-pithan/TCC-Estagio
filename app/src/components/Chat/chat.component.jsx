import { useEffect, useRef, useState } from "react";
import "./chat.css";
import { Message } from "./message.component";

export function Chat({ messages, activeFriend, handleSendMessage }) {
  const [text, setText] = useState("");

  const handleTextChange = (event) => {
    setText(event.target.value);
  };

  const showMessages = messages
    ? messages.map((message, index) => {
        return <Message content={message} key={index} />;
      })
    : null;

  const showActiveFriendCard = activeFriend ? (
    <div className="activeFriendCard">
      <img
        className="activeFriendAvatar"
        src={activeFriend.imageProfileFriend}
        alt="avatar friend"
      />
      <p className="activeFriendName">{activeFriend.usernameFriend}</p>
    </div>
  ) : null;

  const autoScroll = useRef(null);

  useEffect(() => {
    autoScroll.current.scrollTop = autoScroll.current.scrollHeight;
  }, [messages])

  return (
    <div className="chatContainer">
      <header className="chatHeader">{showActiveFriendCard}</header>
      <section className="chatBody" ref={autoScroll}>{showMessages}</section>
      <footer className="chatFooter">
        <input
          className="chatInput"
          type="text"
          placeholder="Escreva aqui..."
          value={text}
          onChange={handleTextChange}
        />
        <button
          className="chatSendButton"
          onClick={() => {
            handleSendMessage(text);
            setText("");
          }}
        >
          {">>"}
        </button>
      </footer>
    </div>
  );
}
