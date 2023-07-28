import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { completeQuest } from "../../api/QUEST/completeQuest";
import { fetchQuests } from "../../api/QUEST/listQuests";
import { fetchCompleteQuests } from "../../api/USER/completeQuests";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

export function useQuests() {
  const [questList, setQuestList] = useState();
  const [completedQuests, setCompletedQuests] = useState();
  const [trigger, setTrigger] = useState(false);
  const [, setModal] = useGlobalMessage();
  const { setLoading } = useLoading();
  const navigate = useNavigate();
  const getQuests = async () => {
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

  const getCompletedQuests = async () => {
    try {
      setLoading(true);
      const res = await fetchCompleteQuests();
      setCompletedQuests(res);
    } catch (error) {
      setModal(
        "Erro ao carregar missões completas, realize login novamente.",
        "error",
        true
      );
      navigate("/");
    } finally {
      setLoading(false);
    }
  };

  const handleComplete = async (id) => {
    try {
      setLoading(true);
      await completeQuest(id);
      setTrigger(true)
    } catch (error) {
      setModal(error.response.data.message, "error", true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getQuests();
    getCompletedQuests();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (trigger) {
      getQuests();
      getCompletedQuests();
      setTrigger(false);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [trigger]);

  return { questList, completedQuests, handleComplete };
}
