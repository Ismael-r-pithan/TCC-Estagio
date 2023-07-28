import { useParams } from "react-router-dom";
import { ProfileBanner } from "../../components/ProfileBanner/profileBanner.component";
import { Button } from "../../components/Button/button.component";
import { useRelationship } from "../../hooks/user/useRelationship.hook";
import { useUserDataId } from "../../hooks/user/useUserDataId.hook";
import { TaskList } from "../../components/TaskList/taskList.component";
import { useDashboard } from "../../hooks/dashboard/useDashboard.hook";
import { useEffect } from "react";
export function PublicProfile() {
  const { id } = useParams();
  const { data } = useUserDataId(id);
  const { friendshipStatus, askNewFriendship } = useRelationship(id);

  const { getPublicTasks, userTasks } = useDashboard();

  useEffect(() => {
    getPublicTasks(id);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const friendshipButton = friendshipStatus === "DESCONHECIDOS" ? (
    <Button
      className="contained-primary medium"
      onClick={askNewFriendship}
    >
      Solicitar amizade
    </Button>
  ) : null;

  return data ? (
    <div className="profileScreen screenContainer">
      <section className="mainPageSection profileSection publicProfileSection">
        <header className="mainPageHeader">
          <h1 className="mainPageTitle">PERFIL</h1>
        </header>
        <ProfileBanner userData={data} />
        <section className="mainSection">
          <div className="sectionContainer">
            <h1 className="sectionTitle">ULTIMAS TAREFAS</h1>
            <div className="requestsContainer">
              <TaskList list={userTasks} />
            </div>
          </div>
          <section className="friendshipSection">
            <h1>{`STATUS DE RELACIONAMENTO : ${friendshipStatus}`}</h1>
            {friendshipButton}
          </section>
        </section>
      </section>
    </div>
  ) : null;
}
