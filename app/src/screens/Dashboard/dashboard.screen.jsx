import { UserCard } from "../../components";
import "./dashboard.css";
import { UserProgress } from "../../components/UserProgress/userProgress.component";
import { TaskList } from "../../components/TaskList/taskList.component";
import { useDashboard } from "../../hooks/dashboard/useDashboard.hook";
import { useFriendship } from "../../hooks/user/useFriendship.hook";
import { FriendsList } from "../../components/FriendsList/friendsList.component";
import { useProfileQuests } from "../../hooks/quests/useProfileQuests.hook";

export function Dashboard() {
  const { userTasks, data, questList } = useDashboard();
  const { friends } = useFriendship();
  const { completedQuests } = useProfileQuests();
  const streak = completedQuests ? completedQuests.length : 0;

  return data && questList ? (
    <div className="dashboardContainer screenContainer">
      <section className="mainPageSection dashboardSection">
        <header className="mainPageHeader dashboardHeader">
          <h1 className="mainPageTitle">
            DASH
            <br />
            BOARD
          </h1>
          <div className="dashboardCards">
            <div className="userCard">
              <UserCard userData={data} />
            </div>
          </div>
        </header>
        <section className="mainSection">
          <div className="sectionContainer">
            <a href="/tarefas">
              <h1 className="sectionTitle">TAREFAS DO DIA {">>"}</h1>
            </a>
            <div className="requestsContainer">
              <TaskList list={userTasks} />
            </div>
          </div>
          <div className="sectionContainer ">
            <a href="/missoes">
              <h1 className="sectionTitle">MISSÕES DISPONÍVEIS {">>"}</h1>
            </a>
            <div className="availableQuestsContainer">
              {questList.map((quest, index) => {
                return (
                  <div className="smallQuestContainer" key={index}>
                    <h1 className="smallQuestTitle">{`${quest.name} - ${quest.recurring} `}</h1>
                    <p className="smallQuestDescription">{quest.description}</p>
                  </div>
                );
              })}
            </div>
          </div>
          <div className="rankContainer">
            <h1 className="sectionTitle">RANK ENTRE AMIGOS</h1>
            <div className="gradientContainer">
              <FriendsList list={friends} />
            </div>
          </div>
        </section>
        <section className="bottomSection">
          <UserProgress user={data} streak={streak} />
        </section>
      </section>
    </div>
  ) : null;
}
