import { useEffect } from "react";
import { useForms } from "../../hooks/form/useForms";
import "./taskFilter.css";

const initialFormFields = {
  date: "",
  category: "",
  priority: "",
};
export function TaskFilter({ page, fetchTasks }) {
  const { form, handleChange } = useForms(initialFormFields);

  useEffect(() => {
    try {
      fetchTasks(form.date, form.category, form.priority);
    } catch (error) {}
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [form, page]);
  return (
    <form className="form">
      <select name="category" className="categoria" onChange={handleChange}>
        <option value="">CATEGORIA</option>
        <option value="LIMPEZA">LIMPEZA</option>
        <option value="HIGIENE">HIGIENE</option>
        <option value="ESTUDO">ESTUDO</option>
        <option value="LAZER">LAZER</option>
        <option value="SAUDE">SAUDE</option>
        <option value="TRABALHO">TRABALHO</option>
      </select>
      <select name="priority" className="prioridade" onChange={handleChange}>
        <option value="">PRIORIDADE</option>
        <option value="URGENTE">URGENTE</option>
        <option value="IMPORTANTE">IMPORTANTE</option>
        <option value="NORMAL">NORMAL</option>
      </select>
      <input
        onChange={handleChange}
        className="dia-semana"
        type="date"
        id="date"
        name="date"
        value={form.date}
      />
    </form>
  );
}
