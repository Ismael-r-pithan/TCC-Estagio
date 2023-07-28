import "./userProgress.css";
import { ProgressBar } from "./progressBar.component";
import { Streak } from "./streak.component";

export function UserProgress({ user, streak }) {
  return (
    <div className="userProgress">
      <div className="progressContainer">
        <h2 className="progressTitle">SEU PROGRESSO</h2>
        <ProgressBar experience={user.experience} />
      </div>
      <div className="streakContainer">
        <Streak streak={streak}/>
      </div>
    </div>
  );
}
