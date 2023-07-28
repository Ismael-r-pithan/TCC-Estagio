import { CompleteQuestCard } from "../../components/CompleteQuestCard/completeQuestCard.component";
import { QuestCard } from "../../components/QuestCard/questCard.component";
import { useQuests } from "../../hooks/quests/useQuests.hook";
import "./questScreen.css";

export function QuestScreen() {
  const { questList, completedQuests, handleComplete } = useQuests();

  return questList && completedQuests ? (
    <div className="questsContainer screenContainer">
      <section className="mainPageSection questsSection">
        <header className="mainPageHeader questsHeader">
          <h1 className="mainPageTitle">MISSÕES</h1>
        </header>
        <section className="questsMainSection">
          <section className="questsListSection">
            <header className="sectionHeader">
              <h1 className="sectionTitle">MISSÕES DISPONÍVEIS</h1>
            </header>
            <div className="availableList">
              {questList.map((quest, index) => {
                return (
                  <QuestCard
                    questData={quest}
                    key={index}
                    handleComplete={handleComplete}
                  />
                );
              })}
            </div>
          </section>
          <section className="questsListSection">
            <header className="sectionHeader">
              <h1 className="sectionTitle">MISSÕES CONCLUÍDAS</h1>
            </header>
            <div className="availableList">
              {completedQuests.map((quest, index) => {
                return <CompleteQuestCard questData={quest} key={index} />;
              })}
            </div>
          </section>
        </section>
      </section>
    </div>
  ) : null;
}
