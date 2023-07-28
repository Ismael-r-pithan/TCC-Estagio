import "./items.css";

export function IconItem({ itemData, setItem }) {
  const ownedText = itemData.owned ? 'Já adquirido' : '';
  return itemData ? (
    <button className="iconItemContainer" onClick={() => setItem(itemData)}>
      <img className="itemIcon" src={itemData.imageUrl} alt="item icon" />
      <h1 className="iconName">{itemData.name}</h1>
      <p className="ownedText">{ownedText}</p>
    </button>
  ) : null;
}
