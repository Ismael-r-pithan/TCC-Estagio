import React from "react";
import HouseHeroLogo from "../../assets/icons/logo.svg";
import { useNavigate } from "react-router-dom";
import { useLogout } from "../../hooks/auth/useLogout.hook";
import { Button } from "../../components";
export function LogoutScreen() {
  const navigate = useNavigate();
  const { logout } = useLogout();

  return (
    <div className="screenContainer homeScreenContainer">
      <form className="formContainer">
        <img
          className="logoImg-big"
          src={HouseHeroLogo}
          alt="House Hero Logo"
        />
        <h1 className="formTitle">LOGOUT</h1>
        <h2  className="containerText">Você tem certeza que deseja realizar o logout?</h2>
        <Button
          className="contained-primary medium"
          type="button"
          onClick={logout}
        >
          LOGOUT
        </Button>
        <Button
          className="contained-primary medium"
          type="button"
          onClick={navigate("/dashboard")}
        >
          VOLTAR
        </Button>
      </form>
    </div>
  );
}
