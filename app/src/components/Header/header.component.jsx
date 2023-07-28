import React from "react";
import HouseHeroLogo from "../../assets/icons/logo.svg";
import { useGlobalUser } from "../../context/user";
import { useToggle } from "../../hooks/UI/useToggle.hook";
import { HamburgerMenu} from "../index";
import "./header.css";

export function Header() {
  const [user,] = useGlobalUser();
  const {toggle, handleClick} = useToggle();

  return (
    <div className="header">
      <nav>
        <div className="logo">
          <img className="logoImg" src={HouseHeroLogo} alt="House Hero" />
          <a href="/" className="logoTitle">
            HOUSE HERO
          </a>
        </div>
      </nav>
      {user? (
        <HamburgerMenu toggle={toggle} handleToggle={handleClick} className="contained-white medium"/>
      ) : null}
    </div>
  );
}
