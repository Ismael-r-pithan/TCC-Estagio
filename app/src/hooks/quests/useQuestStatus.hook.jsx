import { checkQuestStatus } from "../../api/QUEST/checkStatus";
import {  useState } from "react";
import { useGlobalMessage } from "../../context/message";

export function useQuestStatus() {
  const [questStatus, setQuestStatus] = useState();
  const [, setModal] = useGlobalMessage();
  const fetchStatus = async (questId) => {
    try {
      const res = await checkQuestStatus(questId);
      setQuestStatus(res);
    } catch (error) {
      setModal("Erro ao checar progresso.", "error", true);
    }
  };



  return { fetchStatus, questStatus };
}
