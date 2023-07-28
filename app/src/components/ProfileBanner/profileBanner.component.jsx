import "./profileBanner.css";
import defaultBanner from "../../assets/images/defaultBanner.png";
import defaultAvatar from "../../assets/icons/defaultAvatar.png";
import { ProgressBar } from "../UserProgress/progressBar.component";
import { Streak } from "../UserProgress/streak.component";

export function ProfileBanner({ userData, streak }) {
  const profileBanner = userData.bannerUrl
    ? userData.bannerUrl
    : defaultBanner;
  const profileAvatar = userData.imageProfile
    ? userData.imageProfile
    : defaultAvatar;

  return userData ? (
    <section className="profileBannerSection">
      <img
        className="profileBannerImage"
        src={profileBanner}
        alt="User profile banner"
      />
      <div className="profileInfo">
        <div className="profileUserInfo">
          <img
            className="profileAvatar"
            src={profileAvatar}
            alt="User profile avatar"
          />
          <h1 className="profileUsername">{userData.username}</h1>
        </div>
        <div className="profileUserProgressContainer">
          <div className="progressBarContainer">
            <ProgressBar experience={userData.experience} />
          </div>
          <Streak streak={streak}/>
        </div>
      </div>
    </section>
  ) : null;
}
