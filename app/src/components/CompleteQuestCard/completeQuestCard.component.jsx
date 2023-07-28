export function CompleteQuestCard({ questData }) {

  return (
    <div className="questCardContainer">
      <div className="questHeader">
        <h1 className="questName">{questData.questName}</h1>
        <div className="frequencyContainer">
          <h1 className="frequencyText">{`${questData.questExperience}XP`}</h1>
        </div>
      </div>
      <div className="questInfo">
        <p className="questDescription questText">{`Missão completa em ${questData.completedAt}`}</p>
        <p className="questReward questText">{`Recompensa: ${questData.questRewards} pontos`}</p>
      </div>
    </div>
  );
}
