import React from "react";
import HouseHeroLogo from "../../../assets/icons/logo.svg";
import { Button } from "../../Button/button.component";
import { useNavigate } from "react-router-dom";
import { useLogin } from "../../../hooks/auth/useLogin.hook";
import "../form.css";

export function LoginForm() {
  const navigate = useNavigate();
  const { form, handleChange, handleSubmit, enableLogin, touched, handleBlur } = useLogin();

  return (
    <form onSubmit={handleSubmit} className="formContainer">
      <img className="logoImg-big" src={HouseHeroLogo} alt="House Hero Logo" />
      <h1 className="formTitle">LOGIN</h1>
      <div className="formInputContainer">
        <label htmlFor="email">E-MAIL</label>
        <input
          type="text"
          name="email"
          value={form.email}
          onChange={handleChange}
          onBlur={handleBlur}
        />
        {!enableLogin && touched? <p className='errorMsg'>Insira um e-mail válido</p> : null}
      </div>
      <div className="formInputContainer">
        <label htmlFor="password">SENHA</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
        />
        <a className="passwordRecovery" href="/recuperarSenha">
          RECUPERAR SENHA
        </a>{" "}
        {/* tem que botar o link certo aqui pra recuperar senha */}
      </div>
      <div className="formInputButtonsContainer">
        <Button className={`contained-primary medium ${enableLogin? 'enabled':'disabled'}`} type="submit">
          ENTRAR
        </Button>
        <Button
          className="contained-white medium"
          type="button"
          onClick={() => navigate("/register")}
        >
          REGISTRE-SE
        </Button>
        <Button className='contained-white medium' type='button' onClick={() => navigate("/resendEmail")}>
          Reenviar e-mail de ativação
        </Button>
      </div>
    </form>
  );
}
