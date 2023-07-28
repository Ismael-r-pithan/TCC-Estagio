import "./userProgress.css";

export function Streak({ streak }) {
  return (
    <>
        <div className="streak">
          <h2 className="streakTitle">STREAK</h2>
          <h2 className="streakCounter">{streak}</h2>
        </div>
    </>
  );
}

Streak.defaultProps={
  streak: 0,
}
