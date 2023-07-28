import {  useState } from "react";
import { fetchTasks } from "../../api/USER/listTasks";

export function useUserTasks() {
  const [taskList, setTaskList] = useState();
  const [erro, setErro] = useState();
  const [page, setPage] = useState(0);

  const loadMore = () => {
    setPage((prev) => prev + 1);
  };

  const resetPage = async () => {
    setPage((prev) => 0);
  };
  const fetchData = async (userId, date, category, priority, page) => {
    try {
      const data = await fetchTasks(userId, date, category, priority, page);
      setTaskList(data);
    } catch (error) {
      setErro("Falha ao carregar lista de tarefas");
    }
  };

  return { erro, taskList, fetchData, loadMore, resetPage, page };
}
