import "./items.css";

export function BannerItem({ itemData, setItem }) {

  const ownedText = itemData.owned ? 'Já adquirido' : '';

  return itemData ? (
    <button className="bannerItemContainer" onClick={() => setItem(itemData)}>
      <img className="itemBannerImg" src={itemData.imageUrl} alt="item icon" />
      <h1 className="itemName">{itemData.name}</h1>
      <p className="ownedTextbanner">{ownedText}</p>
    </button>
  ) : null;
}
