import HouseHeroLogo from "../../assets/icons/logo.svg";
import { Button } from "../../components";
import { useAccountActivation } from "../../hooks/auth/useAccountActivation.hook";
export function ConfirmEmailScreen() {

    const {handleSubmit} = useAccountActivation();


  return (
    <div className="homeScreenContainer screenContainer">
      <form className="formContainer" onSubmit={handleSubmit}>
        <img
          className="logoImg-big"
          src={HouseHeroLogo}
          alt="House Hero Logo"
        />
        <h1 className="formTitle">ATIVAÇÃO</h1>
        <h1 className="confirmText">Para ativar sua conta, pressione o botão abaixo.</h1>
        <Button className={`contained-primary big`} type="submit">
          ATIVAR CONTA
        </Button>
      </form>
    </div>
  );
}
