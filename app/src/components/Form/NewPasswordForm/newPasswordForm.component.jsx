import React from "react";
import HouseHeroLogo from "../../../assets/icons/logo.svg";
import { Button } from "../../Button/button.component";
import { useNavigate} from "react-router-dom";
import "../form.css";
import { usePasswordChange } from "../../../hooks/auth/usePasswordChange.hook";

export function NewPasswordForm() {

    const navigate = useNavigate();
    const{form, handleChange, handleBlur, passwordValid, handleSubmit} = usePasswordChange();




  return (
    <form className="formContainer" onSubmit={handleSubmit}>
      <img className="logoImg-big" src={HouseHeroLogo} alt="House Hero Logo" />
      <h1 className="smallFormTitle">NOVA SENHA</h1>
      <p className="formInstructions">Insira a sua nova senha.</p>
      <div className="formInputContainer">
        <label htmlFor="password">SENHA</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
        />
      </div>
      <div className="formInputContainer">
        <label htmlFor="confirmPassword">CONFIRME A SENHA</label>
        <input
          type="password"
          name="confirmPassword"
          value={form.confirmPassword}
          onChange={handleChange}
          onBlur={handleBlur}
        />
        {!passwordValid ? <p className='errorMsg'>As senhas devem ser idênticas</p> : null}
      </div>
      <div className="formInputButtonsContainer">
        <Button className={`contained-primary medium ${passwordValid? 'enabled' : 'disabled'}`} type="submit">
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
