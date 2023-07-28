import { CompleteQuestCard } from "../../components/CompleteQuestCard/completeQuestCard.component";
import { FriendsList } from "../../components/FriendsList/friendsList.component";
import { ProfileBanner } from "../../components/ProfileBanner/profileBanner.component";
import { TaskList } from "../../components/TaskList/taskList.component";
import { useDashboard } from "../../hooks/dashboard/useDashboard.hook";
import { useProfileQuests } from "../../hooks/quests/useProfileQuests.hook";
import { useFriendship } from "../../hooks/user/useFriendship.hook";
import "./profileScreen.css";
export function ProfileScreen() {
  const { friends } = useFriendship();
  const { completedQuests } = useProfileQuests();
  const { userTasks, data } = useDashboard();
  const streak = completedQuests ? completedQuests.length : 0

  return data && friends && completedQuests ? (
    <div className="profileScreen screenContainer">
      <section className="mainPageSection profileSection">
        <header className="mainPageHeader">
          <h1 className="mainPageTitle">PERFIL</h1>
        </header>
        <ProfileBanner userData={data} streak={streak}/>
        <section className="mainSection">
          <div className="sectionContainer profileContainer">
            <h1 className="sectionTitle profileTitle">
              ULTIMAS TAREFAS REALIZADAS
            </h1>
            <div className="requestsContainer">
              <TaskList list={userTasks} />
            </div>
          </div>
          <div className="sectionContainer profileQuestsContainer">
            <h1 className="sectionTitle">MISSÕES COMPLETAS</h1>
            {completedQuests.map((quest, index) => {
              return <CompleteQuestCard questData={quest} key={index} />;
            })}
          </div>
          <div className="rankContainer">
            <h1 className="sectionTitle">AMIGOS</h1>
            <div className="gradientContainer friendsContainer">
              {" "}
              <FriendsList list={friends} />
            </div>
          </div>
        </section>
      </section>
    </div>
  ) : null;
}
