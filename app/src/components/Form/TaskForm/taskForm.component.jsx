import React from "react";
import { createTask } from "../../../api/TASK/createTask";
import { useForms } from "../../../hooks/form/useForms";
import { useNavigate } from "react-router-dom";
import "./taskForm.css";
import { getCurrentDate } from "../../../utils/getCurrentDate";
import { alterTask } from "../../../api/TASK/alterTask";

export function TaskForm({ taskData }) {
  const navigate = useNavigate();
  const { form, handleChange, handleSubmit, handleCheckboxChange } = useForms(formFields(), onSubmit);

  function formFields() {
    if (taskData) {
      return {
        taskId: taskData.id,
        name: taskData.name,
        description: taskData.description,
        startTime: taskData.startTime,
        endTime: taskData.endTime,
        scheduledDate: taskData.scheduledDate,
        recurring: taskData.recurring,
        category: taskData.category,
        priority: taskData.priority,
        visibility: taskData.visibility,
      };
    }
    return {
      name: "",
      description: "",
      startTime: "",
      endTime: "",
      scheduledDate: "",
      recurring: false,
      category: "LIMPEZA",
      priority: "URGENTE",
      visibility: "PRIVADO",
    };
  }

  async function onSubmit(form) {
    if (taskData) {
      await onSubmitAlter(form);
    }
    await onSubmitCreate(form);
  }

  async function onSubmitCreate(form) {
    const response = await createTask(form);

    if (response) navigate(0);
  }

  async function onSubmitAlter(form) {
    const response = await alterTask(form);

    if (response) navigate(0);
  }

  function renderBtnSubmitTxt() {
    if (taskData) {
      return "Editar";
    }
    return "Criar";
  }

  return (
    <form className="containerTaskDetail" onSubmit={handleSubmit}>
      <div className="fieldContainer">
        <label htmlFor="name">
          <h1>NOME</h1>
        </label>
        <input
          value={form.name}
          onChange={handleChange}
          name="name"
          type="text"
        />
      </div>
      <div className="fieldContainer">
        <label htmlFor="description">
          <h1>DESCRIÇÃO</h1>
        </label>
        <textarea
          value={form.description}
          onChange={handleChange}
          name="description"
          className="description"
        />
      </div>

      <div className="fieldsContainer">
        <div className="fieldContainer">
          <label htmlFor="startTime">
            <h1>HORÁRIO INICIO</h1>
          </label>
          <input
            value={form.startTime}
            onChange={handleChange}
            name="startTime"
            type="time"
          />
        </div>

        <div className="fieldContainer">
          <label htmlFor="endTime">
            <h1>HORÁRIO FIM</h1>
          </label>
          <input
            value={form.endTime}
            onChange={handleChange}
            name="endTime"
            type="time"
            min={form.startTime}
          />
        </div>
      </div>
      <div className="fieldsContainer">
        <div className="fieldContainer">
          <label htmlFor="scheduledDate" className="scheduled-date">
            <h1>DATA PROGRAMADA</h1>
          </label>

          <input
            value={form.scheduledDate}
            onChange={handleChange}
            name="scheduledDate"
            type="date"
            className="scheduled-date"
            min={getCurrentDate()}
          />
        </div>
        <div className="fieldContainer">
          <label htmlFor="recurring" className="recurring">
            <h1>RECORRENTE?</h1>
          </label>
        </div>
        <div className="fieldContainer">
          <input
            checked={form.recurring}
            value={form.recurring}
            onChange={handleCheckboxChange}
            name="recurring"
            type="checkbox"
            className="recurring"
          />
        </div>
      </div>

      <div className="fieldsContainer">
        <div className="fieldContainer">
          <label htmlFor="category">
            <h1>CATEGORIA</h1>
          </label>
          <select className="select" name="category" onChange={handleChange}>
            <option value="LIMPEZA">LIMPEZA</option>
            <option value="HIGIENE">HIGIENE</option>
            <option value="ESTUDO">ESTUDO</option>
            <option value="LAZER">LAZER</option>
            <option value="SAUDE">SAUDE</option>
            <option value="TRABALHO">TRABALHO</option>
          </select>
        </div>
        <div className="fieldContainer">
          <label htmlFor="priority">
            <h1>PRIORIDADE</h1>
          </label>
          <select className="select" name="priority" onChange={handleChange}>
            <option value="URGENTE">URGENTE</option>
            <option value="IMPORTANTE">IMPORTANTE</option>
            <option value="NORMAL">NORMAL</option>
          </select>
        </div>
        <div className="fieldContainer">
          <label htmlFor="visibility">
            <h1>VISIBILIDADE</h1>
          </label>
          <select className="select" name="visibility" onChange={handleChange}>
            <option value="PRIVADO">PRIVADO</option>
            <option value="PUBLICO">PUBLICO</option>
            <option value="AMIGOS">AMIGOS</option>
          </select>
        </div>
      </div>
      <button className="createButton">{renderBtnSubmitTxt()}</button>
    </form>
  );
}
