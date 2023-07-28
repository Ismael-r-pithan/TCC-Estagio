import { Button } from "../../components/index.js";
import { useQuestStatus } from "../../hooks/quests/useQuestStatus.hook";
import "./questCard.css";

export function QuestCard({ questData, handleComplete }) {
  const { fetchStatus, questStatus } = useQuestStatus();

  return (
    <div className="questCardContainer">
      <div className="questHeader">
        <h1 className="questName">{questData.name}</h1>
        <div className="frequencyContainer">
          <h1 className="frequencyText">{questData.recurring}</h1>
        </div>
      </div>
      <div className="questInfo">
        <p className="questDescription questText">{questData.description}</p>
        <p className="questReward questText">{`Recompensa: ${questData.rewards} pontos`}</p>
      </div>
      {questStatus !== "FINALIZADA" ? (
        <Button
          className="contained-primary medium"
          onClick={() => {
            handleComplete(questData.id);
            fetchStatus(questData.id);
          }}
        >
          <p>CHECAR PROGRESSO</p>
        </Button>
      ) : null}
    </div>
  );
}
