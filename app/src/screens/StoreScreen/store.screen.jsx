import houseHeroLogo from "../../assets/icons/logo.svg";
import { BannerItem } from "../../components/StoreItem/bannerItem.component";
import { IconItem } from "../../components/StoreItem/iconItem.component";
import { ItemModal } from "../../components/StoreItem/itemModal.component";
import { useStore } from "../../hooks/store/useStore.hook";
import { useTransaction } from "../../hooks/store/useTransaction.hook";
import "./store.css";

export function StoreScreen() {
  const {
    icons,
    banners,
    handleChangeAvatar,
    handleChangeBanner,
    setFetchTrigger,
    userData,
  } = useStore();
  const { setSelectedItem, selectedItem, showModal, setShowModal, handleBuy } =
    useTransaction(setFetchTrigger);

  const bannerItems = banners
    ? banners.map((banner, index) => {
        return (
          <BannerItem key={index} itemData={banner} setItem={setSelectedItem} />
        );
      })
    : null;

  const avatarItems = icons
    ? icons.map((icon, index) => {
        return (
          <IconItem key={index} itemData={icon} setItem={setSelectedItem} />
        );
      })
    : null;

  return userData ? (
    <div className="storeContainer screenContainer">
      <section className="mainPageSection storeSection">
        <header className="mainPageHeader storeHeader">
          <h1 className="mainPageTitle">
            LOJA <br />
            DE PONTOS
          </h1>
          <div className="totalPointsContainer">
            <img className="pointsLogo" src={houseHeroLogo} alt="main logo" />
            <h1 className="pointsText">{`${userData.money}(HH POINTS)`}</h1>
          </div>
        </header>
        <section className="storeMainSection">
          <section className="storeItemsSection storeIconSection">
            <header className="iconsHeader">
              <h1 className="storeSectionTitle">ÍCONES DE PERFIL</h1>
            </header>
            <div className="itemsContent iconsContent">{avatarItems}</div>
          </section>
          <section className="storeItemsSection storeBannerSection">
            <header className="bannerHeader">
              <h1 className="storeSectionTitle">CAPAS DE PERFIL</h1>
            </header>
            <div className="itemsContent bannerContent">{bannerItems}</div>
          </section>
        </section>
      </section>
      {showModal && (
        <ItemModal
          itemData={selectedItem}
          unselect={setSelectedItem}
          enabled={setShowModal}
          handleBuy={handleBuy}
          handleAvatar={handleChangeAvatar}
          handleBanner={handleChangeBanner}
        />
      )}
    </div>
  ) : null;
}
