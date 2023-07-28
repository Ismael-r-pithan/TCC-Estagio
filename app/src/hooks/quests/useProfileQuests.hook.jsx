import { useEffect, useState } from "react";
import { fetchCompleteQuests } from "../../api/USER/completeQuests";
import { useNavigate } from "react-router-dom";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

export function useProfileQuests() {
  const [completedQuests, setCompletedQuests] = useState();
  const [, setModal] = useGlobalMessage();
  const { setLoading } = useLoading();
  const navigate = useNavigate();

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

  useEffect(() => {
    getCompletedQuests();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return { completedQuests };
}
