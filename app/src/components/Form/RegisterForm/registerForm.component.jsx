import React from "react";
import HouseHeroLogo from "../../../assets/icons/logo.svg";
import { Button } from "../../Button/button.component";
import { useNavigate } from "react-router-dom";
import "../form.css";
import { useRegister } from "../../../hooks/auth/useRegister.hook";

export function RegisterForm() {
  const navigate = useNavigate();
  const {
    form,
    handleChange,
    handleSubmit,
    handleEmailBlur,
    handlePasswordBlur,
    pswdValid,
    emailValid,
    emailTouched,
    pswdTouched,
    enableRegister,
    confirmPswdTouched,
    confirmPswdValid,
    handleConfirmPasswordBlur
  } = useRegister();

  return (
    <form onSubmit={handleSubmit} className="formContainer">
      <img className="logoImg-big" src={HouseHeroLogo} alt="House Hero Logo" />
      <h1 className="formTitle">REGISTRAR</h1>
      <div className="formInputContainer">
        <label htmlFor="email">E-MAIL</label>
        <input
          type="text"
          name="email"
          value={form.email}
          onChange={handleChange}
          onBlur={handleEmailBlur}
        />
        {!emailValid && emailTouched? <p className='errorMsg'>Insira um e-mail válido</p> : null}
      </div>
      <div className="formInputContainer">
        <label htmlFor="username">NOME DE USUÁRIO</label>
        <input
          type="text"
          name="username"
          value={form.username}
          onChange={handleChange}
        />
      </div>
      <div className="formInputContainer">
        <label htmlFor="password">SENHA</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
          onBlur={handlePasswordBlur}
        />
        {!pswdValid && pswdTouched? <p className='errorMsg'>Sua senha deve ter no mínimo 8 caracteres, um caracter especial e um minúsculo e maiúsculo.</p> : null}
      </div>
      <div className="formInputContainer">
        <label htmlFor="password">CONFIRMAR SENHA</label>
        <input
          type="password"
          name="confirmPassword"
          value={form.confirmPassword}
          onChange={handleChange}
          onBlur={handleConfirmPasswordBlur}
        />
        {!confirmPswdValid && confirmPswdTouched? <p className='errorMsg'>As senhas devem ser idênticas.</p> : null}
      </div>
      <div className="formInputButtonsContainer">
        <Button
          className={`contained-primary medium ${
            enableRegister ? "enabled" : "disabled"
          }`}
          type="submit"
        >
          REGISTRE-SE
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
