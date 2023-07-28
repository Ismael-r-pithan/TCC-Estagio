import "./userProgress.css";

const EXPERIENCE_PER_LVL = 100;
export function ProgressBar({ experience }) {
  const currentLvl = Math.floor(experience / EXPERIENCE_PER_LVL);
  const nextLvl = currentLvl + 1;
  const progress = 100 - ((nextLvl * EXPERIENCE_PER_LVL) - experience);

  return (
    <div className="progressBarContainer">
      <p className="progressXp">TOTAL: {experience} XP</p>
      <div
        style={{
          width: "100%",
          height: 20,
          marginTop: 6,
          borderRadius: 1000000000,
          background: `linear-gradient(90deg, #00ffa3 ${progress}%, #ffffff ${progress}%)`,
        }}
      />
      <div className="progressLvlContainer">
        <p className="progressLvl">LVL {currentLvl}</p>
        <p className="progressLvl">LVL {nextLvl}</p>
      </div>
    </div>
  );
}

ProgressBar.defaultProps={
  experience: 0
}
