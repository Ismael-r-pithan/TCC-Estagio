import "./publicUserCard.css";

export function PublicUserCard({ userData }) {
  return (
    <div className="publicCard">
      <header className="publicBannerImg">
        <img
          className="publicUserBanner"
          src={userData.bannerUrl}
          alt="user banner"
        />
      </header>
      <footer className="publicCardFooter">
        <div className="publicUserAvatarContainer">
          <img
            className="publicUserAvatar"
            src={userData.imageProfile}
            alt="user avatar"
          />
          <a href={`/perfil/${userData.id}`}><p className="userCardName">{userData.username}</p></a>
        </div>
      </footer>
    </div>
  );
}
