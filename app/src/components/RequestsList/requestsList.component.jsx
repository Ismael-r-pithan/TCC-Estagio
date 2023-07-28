import { Button } from "../Button/button.component";
import defaultAvatar from "../../assets/icons/defaultAvatar.png";
import "./requestsList.css";

function RequestCard({ requestData, onAnswer }) {
  return (
    <div className="requestCard">
      <div className="requestInfo">
        <img
          className="userAvatar"
          src={requestData.imageProfileFriend || defaultAvatar}
          alt="Avatar do usuário"
        />
        <div className="requestNameEmail">
        <h1 className="usernameTitle">{requestData.usernameFriend}</h1>
        <p className="friendrequestEmail">{requestData.emailFriend}</p>
        </div>
      </div>
      <Button className="contained-primary" onClick={() => onAnswer('ACEITA', requestData.idRequest)}>Aceitar</Button>
      <Button className="contained-primary" onClick={() => onAnswer('REJEITADA', requestData.idRequest)}>Recusar</Button>
    </div>
  );
}

export function RequestsList({ list, onAnswer, renderTrigger }) {
  return list ? (
    <div className="friendsRequestsContainer">
      {list.map((request, index) => {
        return <RequestCard requestData={request} key={index} onAnswer={onAnswer}/>;
      })}
    </div>
  ) : null;
}
