import { useUsers } from "../../hooks/user/useUsers.hook";
import { Button } from "../../components/index";
import "./userSearchbox.css";
import { PublicUserCard } from "../PublicUserCard/publicUserCard.component";

export function UserSearchbox() {
  const { userList, fetchData, handleChange, searchInput } = useUsers();

  return (
    <div className="searchboxContainer">
      <header className="searchboxHeader">
        <h1 className="searchboxTitle">EXPLORAR</h1>
        <input
          className="searchboxInput"
          type="text"
          placeholder="PESQUISAR POR NOME"
          value={searchInput}
          onChange={handleChange}
        />
        <Button className="extrasmall" onClick={fetchData}>
          OK
        </Button>
      </header>
      <div className="userResults">
        {userList ? (
          userList.map((user, index) => {
            return <PublicUserCard userData={user} key={index} />;
          })
        ) : (
          <p>Insira um nome de usuário e aperte OK</p>
        )}
      </div>
    </div>
  );
}
