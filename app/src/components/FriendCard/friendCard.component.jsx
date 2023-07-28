import "./friendCard.css";
import { Button } from "../Button/button.component";
import { useNavigate } from "react-router-dom";

export function FriendCard({ friendData, handleChat }) {
  const navigate = useNavigate();
  const chatOnClick = handleChat
    ? () => {
        handleChat(friendData);
        window.location.reload();
      }
    : () => navigate("/meusAmigos");

  return (
    <div className="friendCardContainer">
      <div className="friendCardInfo">
        <img
          className="friendAvatar"
          src={friendData.imageProfileFriend}
          alt="Friend profile avatar"
        />
        <p className="expFriend">{friendData.experienceFriend}XP</p>
        <a href={`/perfil/${friendData.idFriend}`}>
          <p className="friendUsername">{friendData.usernameFriend}</p>
        </a>
      </div>
      <div className="friendCardButtons">
        <Button className="contained-primary small" onClick={chatOnClick}>
          Chat
        </Button>
      </div>
    </div>
  );
}
