import { Button } from "../Button/button.component";
import houseHeroIcon from "../../assets/icons/logo.svg";
import "./items.css";

export function ItemModal({
  itemData,
  enabled,
  unselect,
  handleBuy,
  handleAvatar,
  handleBanner,
}) {
  const buyButton = (
    <Button className="storeButton" onClick={handleBuy}>
      <img className="smallLogo" src={houseHeroIcon} alt="small icon" />
      <p className="price">{`COMPRAR [${itemData.price}HH Pontos]`}</p>
    </Button>
  );

  const useButton = (
    <Button
      className="storeButton"
      onClick={
        itemData.type === "AVATAR"
          ? () => handleAvatar(itemData.id)
          : () => handleBanner(itemData.id)
      }
    >
      <p className="price">Usar no perfil</p>
    </Button>
  );

  return (
    <div className="screenOverlay">
      <div className="storeModal">
        <h1 className="itemModalTitle">{itemData.name.toUpperCase()}</h1>
        <img
          className="itemModalImage"
          src={itemData.imageUrl}
          alt="store item"
        />
        {itemData.owned ? useButton : buyButton}
        <Button
          className="contained-white medium"
          onClick={() => {
            unselect();
            enabled(false);
          }}
        >
          VOLTAR
        </Button>
      </div>
    </div>
  );
}
