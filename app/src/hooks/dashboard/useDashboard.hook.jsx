import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchTodayTasks } from "../../api/USER/listTodayTasks";
import { useGlobalMessage } from "../../context/message";
import { getCurrentDate } from "../../utils/getCurrentDate";
import { useValidateUser } from "../auth/useValidateUser.hook";
import { useLoading } from "../context/useLoading.hook";
import { useUserData } from "../user/useUserData.hook";
import { fetchQuests } from "../../api/QUEST/listQuests";
export function useDashboard() {
  const [userTasks, setUserTasks] = useState();
  const [questList, setQuestList] = useState();
  const { data } = useUserData();
  const navigate = useNavigate();
  const [, setModal] = useGlobalMessage();
  const { setLoading } = useLoading();
  const { isUserAuth } = useValidateUser();

  const getTasks = async () => {
    try {
      setLoading(true);
      const res = await fetchTodayTasks(data.id, getCurrentDate());
      setUserTasks(res);
    } catch (error) {
      setModal(
        "Falha ao carregar tarefas do dia, realize login novamente",
        "error",
        true
      );
      navigate("/");
    } finally {
      setLoading(false);
    }
  };

  const getAvailableQuests = async () => {
    try {
      setLoading(true);
      const res = await fetchQuests();
      setQuestList(res);
    } catch (error) {
      setModal(
        "Erro ao carregar missões, realize login novamente.",
        "error",
        true
      );
      navigate("/");
    } finally {
      setLoading(false);
    }
  };

  const getPublicTasks = async (userId) => {
    try {
      setLoading(true);
      const res = await fetchTodayTasks(userId, getCurrentDate());
      setUserTasks(res);
    } catch (error) {
      setModal(
        "Falha ao carregar tarefas do dia, realize login novamente",
        "error",
        true
      );
      navigate("/");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isUserAuth && data) {
      getTasks();
      getAvailableQuests();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isUserAuth, data]);

  return { userTasks, data, getPublicTasks,questList };
}
