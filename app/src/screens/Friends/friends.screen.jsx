import { Chat } from "../../components/Chat/chat.component";
import { FriendsList } from "../../components/FriendsList/friendsList.component";
import { RequestsList } from "../../components/RequestsList/requestsList.component";
import { UserSearchbox } from "../../components/UserSearchbox/userSearchbox.component";
import { useChat } from "../../hooks/chat/useChat.hook";
import { useFriendship } from "../../hooks/user/useFriendship.hook";
import "./friendsScreen.css";
export function FriendsScreen() {
  const { requests, friends, answerFriendship } = useFriendship();
  const { messages, setActiveFriend, activeFriend, sendMessage } = useChat();

  return (
    <div className="friendsScreen screenContainer">
      <section className="mainPageSection friendsSection">
        <section className="exploreSection">
          <UserSearchbox />
        </section>
        <header className="mainPageHeader">
          <h1 className="mainPageTitle">AMIGOS</h1>
        </header>
        <section className="mainSection">
          <div className="sectionContainer">
            <h1 className="sectionTitle">SOLICITAÇÕES</h1>
            <RequestsList list={requests} onAnswer={answerFriendship} />
          </div>
          <div className="sectionContainer ">
            <h1 className="sectionTitle">MEUS AMIGOS</h1>
            <FriendsList list={friends} handleChat={setActiveFriend} />
          </div>
          <div className="rankContainer">
            <h1 className="sectionTitle">CONVERSAS</h1>
            <div className="gradientContainer">
              <Chat
                messages={messages}
                activeFriend={activeFriend}
                handleSendMessage={sendMessage}
              />
            </div>
          </div>
        </section>
      </section>
    </div>
  );
}
