import defaultAvatar from "../../assets/icons/defaultAvatar.png";
import "./userCard.css";

export function UserCard({ userData }) {
  const userAvatar = userData.imageProfile || defaultAvatar;

  return userData ? (
    <div className="userCardContainer">
      <img className="userAvatar" src={userAvatar} alt="Avatar do usuário" />
      <a href="/meuPerfil">
        <h1 className="userCardText">{`Olá, ${userData.username}`}</h1>
      </a>
    </div>
  ) : null;
}
