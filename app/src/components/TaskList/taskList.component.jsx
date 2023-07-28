import "./taskList.css";
import { getWeekDayString } from "../../utils/getWeekDayString";

function TaskCard({ taskData, onSelectTask }) {
  const checkCircle = taskData.finishedDate ? "check-circle" : "";

  return (
    <div
      className="container"
      onClick={() => {
        onSelectTask(taskData);
      }}
    >
      <div className={`circle ${checkCircle}`}> </div>
      <div className="title">{taskData.name}</div>
      <div className="button">{taskData.category}</div>
      <div className="important">{taskData.priority}</div>
      <div className="weekday">{getWeekDayString(taskData.scheduledDate)}</div>
    </div>
  );
}

export function TaskList({ list, onSelectTask }) {
  return list ? (
    <>
      {list.map((task, index) => {
        return (
          <TaskCard key={index} taskData={task} onSelectTask={onSelectTask} />
        );
      })}
    </>
  ) : null;
}

TaskList.defaultProps = {
  list: [],
  onSelectTask: () => {},
};
