import React from "react";
import HouseHeroLogo from "../../../assets/icons/logo.svg";
import { Button } from "../../Button/button.component";
import { useNavigate } from "react-router-dom";
import "../form.css";
import { usePasswordRecovery } from "../../../hooks/auth/usePasswordRecovery.hook";

export function PasswordRecoveryForm() {

    const navigate = useNavigate();
    const { email, handleChange, handleSubmit,  enableRecovery, touched, handleBlur } = usePasswordRecovery();

  return (
    <form className="formContainer" onSubmit={handleSubmit}>
      <img className="logoImg-big" src={HouseHeroLogo} alt="House Hero Logo" />
      <h1 className="smallFormTitle">RECUPERAÇÃO DE SENHA</h1>
      <p className="formInstructions">Insira seu e-mail para receber o link de recuperação de senha.</p>
      <div className="formInputContainer">
        <label htmlFor="email">E-MAIL</label>
        <input
          type="text"
          name="email"
          value={email}
          onChange={handleChange}
          onBlur={handleBlur}
        />
        {!enableRecovery && touched? <p className='errorMsg'>Insira um e-mail válido</p> : null}
      </div>
      <div className="formInputButtonsContainer">
        <Button className={`contained-primary medium ${enableRecovery? "enabled" : "disabled"}`} type="submit">
          OK
        </Button>
        <Button
          className="contained-white medium"
          type="button"
          onClick={() => navigate("/")}
        >
          VOLTAR
        </Button>
      </div>
    </form>
  );
}
