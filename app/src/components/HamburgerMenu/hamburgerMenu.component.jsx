
import { SideMenu } from "../SideMenu/sideMenu.component";
import "./hamburgerMenu.css";

export function HamburgerMenu({ toggle, handleToggle }) {
  return (
    <div className="hamburgerMenuContainer">
      <button
        className={`hamburgerMenu ${toggle ? "on" : "off"}`}
        onClick={handleToggle}
      >
        <div className="hamburgerLine" />
        <div className="hamburgerLine" />
        <div className="hamburgerLine" />
      </button>
      <SideMenu toggle={toggle} />
    </div>
  );
}
