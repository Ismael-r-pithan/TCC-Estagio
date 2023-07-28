import "./tasks.css";
import { useGlobalUser } from "../../context/user";
import { TaskList } from "../../components/TaskList/taskList.component";
import { useUserTasks } from "../../hooks/user/useUserTasks.hook";
import { useEffect, useState } from "react";
import { TaskFilter } from "../../components/TaskFilter/taskFilter.component";
import { TaskDetail } from "../../components/TaskDetail/taskDetail";
import { TaskForm } from "../../components/Form/TaskForm/taskForm.component";

export function TasksScreen() {
  const [user] = useGlobalUser();
  const { taskList, fetchData, loadMore, resetPage, page } = useUserTasks();
  const [taskSelected, setTaskSelected] = useState();

  function onSelectTask(taskId) {
    setTaskSelected((prevTask) => taskId);
  }

  async function fetchTasks(date, category, priority) {
    fetchData(user.id, date, category, priority, page);
  }

  function onClickNewTask() {
    setTaskSelected((prevTask) => null);
  }

  useEffect(() => {
    fetchData(user.id, "", "", "");
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function renderLoadMore() {
    if (taskList.length === 10)
      return <button onClick={loadMore}>Carregar mais</button>;
    return <></>;
  }

  function renderResetPage() {
    if (page > 0)
      return <button onClick={resetPage}>Voltar para primeira pagina</button>;
    return <></>;
  }

  function renderTaskDetail() {
    if (!taskSelected) {
      return (
        <>
          <h1 className="sectionTitle">NOVA TAREFA</h1>
          <TaskForm />
        </>
      );
    }
    return (
      <>
        <h1 className="sectionTitle">TAREFA: {taskSelected.id}</h1>
        <TaskDetail taskData={taskSelected} />
      </>
    );
  }

  return taskList ? (
    <div className="dashboardContainer screenContainer">
      <section className="mainPageSection dashboardSection">
        <header className="mainPageHeader">
          <h1 className="mainPageTitle">TAREFAS</h1>
        </header>
        <section className="mainSection">
          <div className="sectionContainer">
            <div className="headerTasks">
              <h1 className="sectionTitle">SUAS TAREFAS</h1>
              <TaskFilter page={page} fetchTasks={fetchTasks} />
            </div>
            <div className="requestsContainer">
              <button className="btnCriarTarefa" onClick={onClickNewTask}>
                CRIAR TAREFA
              </button>
              <TaskList list={taskList} onSelectTask={onSelectTask} />
              {renderLoadMore()}
              {renderResetPage()}
            </div>
          </div>
          <div className="sectionContainer ">{renderTaskDetail()}</div>
        </section>
      </section>
    </div>
  ) : null;
}
