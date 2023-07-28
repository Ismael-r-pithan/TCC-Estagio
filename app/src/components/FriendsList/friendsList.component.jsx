import "./friendsList.css";
import { FriendCard } from "../FriendCard/friendCard.component";

export function FriendsList({ list, handleChat }) {
  return list ? (
    <div className="friendsListContainer">
      {list.map((friend, index) => {
        return <FriendCard key={index} friendData={friend} handleChat={handleChat} />;
      })}
    </div>
  ) : null;
}
