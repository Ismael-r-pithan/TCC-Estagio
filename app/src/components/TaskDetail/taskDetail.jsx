import "./taskDetail.css";
import { finishTask } from "../../api/TASK/finishTask";
import { resetTask } from "../../api/TASK/resetTask";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { deleteTask } from "../../api/TASK/deleteTask";
import { TaskForm } from "../Form/TaskForm/taskForm.component";

const RECURRING_TEXT = ["NORMAL", "RECORRENTE"];
export function TaskDetail({ taskData }) {
  const navigate = useNavigate();
  const [msgOnDelete, setMsgOnDelete] = useState();
  const [msgOnAlter, setMsgOnAlter] = useState();
  const [altering, setAltering] = useState();

  useEffect(() => {
    setMsgOnDelete();
    setMsgOnAlter();
    setAltering();
  }, [taskData]);

  async function onFinish() {
    await finishTask(taskData.id);
    navigate(0);
  }
  async function onReset() {
    await resetTask(taskData.id);
    navigate(0);
  }

  async function onDeleteConfirm() {
    await deleteTask(taskData.id);
    navigate(0);
  }

  async function onDelete() {
    setMsgOnDelete("TEM CERTEZA?");
  }

  async function onAlter() {
    setMsgOnAlter("TEM CERTEZA?");
  }

  async function onAlterConfirm() {
    setAltering(true);
  }

  function renderBtnDelete() {
    if (msgOnDelete) {
      return (
        <button className="btnDelete" onClick={onDeleteConfirm}>
          {msgOnDelete}
        </button>
      );
    }
    return (
      <button className="btnDelete" onClick={onDelete}>
        {"EXCLUIR"}
      </button>
    );
  }

  function renderBtnAlter() {
    if (msgOnAlter) {
      return (
        <button className="btnEdit" onClick={onAlterConfirm}>
          {msgOnAlter}
        </button>
      );
    }
    return (
      <button className="btnEdit" onClick={onAlter}>
        {"EDITAR"}
      </button>
    );
  }

  function renderBtnFinishReset() {
    if (taskData.finishedDate) {
      return (
        <button className="btnFinish" onClick={onReset}>
          {"RESETAR"}
        </button>
      );
    }
    return (
      <button className="btnFinish" onClick={onFinish}>
        {"FINALIZAR"}
      </button>
    );
  }

  return altering && taskData ? (
    <TaskForm taskData={taskData} />
  ) : (
    <div className="containerTaskDetail">
      <div className="fieldContainer">
        <h1>NOME</h1>
        <p>{taskData.name}</p>
      </div>
      <div className="fieldContainer">
        <h1>DESCRIÇÃO</h1>
        <p className="taskDescription">{taskData.description}</p>
      </div>
      <div className="fieldsContainer">
        <div className="fieldContainer">
          <h1>HORÁRIO INICIO</h1>
          <p>{taskData.startTime}</p>
        </div>
        <div className="fieldContainer">
          <h1>HORÁRIO FIM</h1>
          <p>{taskData.endTime}</p>
        </div>
      </div>
      <div className="fieldsContainer">
        <div className="fieldContainer">
          <h1>DATA PROGRAMADA</h1>
          <p>{taskData.scheduledDate}</p>
        </div>
        <div className="fieldContainer">
          <p>{RECURRING_TEXT[Number(taskData.recurring)]}</p>
        </div>
      </div>
      <div className="fieldsContainer">
        <div className="fieldContainer">
          <h1>CATEGORIA</h1>
          <p>{taskData.category}</p>
        </div>
        <div className="fieldContainer">
          <h1>PRIORIDADE</h1>
          <p>{taskData.priority}</p>
        </div>
        <div className="fieldContainer">
          <h1>VISIBILIDADE</h1>
          <p>{taskData.visibility}</p>
        </div>
      </div>
      <div className="fieldsContainer">
        <div className="fieldContainer">
          <h1>FINALIZADA EM:</h1>
          <p>{taskData.finishedDate}</p>
        </div>
        {renderBtnFinishReset()}
      </div>
      <div className="fieldsContainer">
        {renderBtnDelete()}
        {renderBtnAlter()}
      </div>
    </div>
  );
}
