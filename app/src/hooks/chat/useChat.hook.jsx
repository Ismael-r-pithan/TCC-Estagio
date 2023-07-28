import { Stomp } from "@stomp/stompjs";
import { useGlobalUser } from "../../context/user";
import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { findChatMessages } from "../../api/CHAT/findChatMessages";
import { useGlobalActiveFriend } from "../../context/activeChat";

const CHAT_API = "http://localhost:8080/api/ws";
var stompClient = null;

export function useChat() {
  const [user] = useGlobalUser();
  const [activeFriend, setActiveFriend] = useGlobalActiveFriend();
  const [messages, setMessages] = useState();

  

  const connect = () => {
    const socket = new SockJS(CHAT_API);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    stompClient.subscribe(
      "/user/" + user.id + "/queue/messages",
      onMessageReceived
    );
  };

  const onError = (err) => {
    console.log(err);
  };

  const onMessageReceived = async (msg) => {
    const notification = JSON.parse(msg.body);
    if (activeFriend.idFriend === notification.senderId) {
      const msgList = await findChatMessages(activeFriend.idFriend, user.id)
      setMessages(msgList);
    } 
  };

  const sendMessage = (msg) => {
    if (msg.trim() !== "") {
      const message = {
        senderId: user.id,
        receiverId: activeFriend.idFriend,
        senderName: user.username,
        receiverName: activeFriend.usernameFriend,
        msgContent: msg,
        date: new Date(),
      };
      stompClient.send("/app/chat", {}, JSON.stringify(message));

      const newMessages = [...messages];
      newMessages.push(message);
      setMessages(newMessages);
    }
  };

  const loadChatMessages = async (friendId, userId) => {
    try {
      const msgs = await findChatMessages(friendId, userId);
      setMessages(msgs);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    connect();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (!activeFriend) return;
    loadChatMessages(activeFriend.idFriend, user.id);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeFriend]);



  return { messages, setActiveFriend, activeFriend, sendMessage };
}
