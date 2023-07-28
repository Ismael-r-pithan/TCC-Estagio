import { useEffect, useState } from "react";
import { answerRequest } from "../../api/USER/answerRequest";
import { fetchFriends } from "../../api/USER/friendlist";
import { fetchRequests } from "../../api/USER/friendshipRequests";
import { useGlobalMessage } from "../../context/message";
import { orderFriendsByExpTop10 } from "../../utils/orderFriendsByExpTop10";
import { useValidateUser } from "../auth/useValidateUser.hook";

export function useFriendship() {
  const [requests, setRequests] = useState();
  const [friends, setFriends] = useState();
  const [fetchTrigger, setFetchTrigger] = useState(false);
  const [, setModal] = useGlobalMessage();
  const { isUserAuth } = useValidateUser();

  const fetchData = async () => {
    try {
      const requestList = await fetchRequests();
      const friendsList = await fetchFriends();

      setRequests(requestList);
      setFriends(friendsList.content);
      setFetchTrigger(false);
    } catch (error) {
      setModal(
        "Erro ao requisitar solicitações, faça o login novamente",
        "error",
        true
      );
    }
  };

  const answerFriendship = async (answer, idRequest) => {
    const message = answer === "ACEITA" ? "aceito" : "rejeitado";

    try {
      await answerRequest(answer, idRequest);
      setModal(`Pedido de amizade ${message} com sucesso.`, "message", true);
      setFetchTrigger(true);
    } catch (error) {
      setModal("Falha ao responder pedido de amizade.", "error", true);
    }
  };

  useEffect(() => {
    if (fetchTrigger) {
      fetchData();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [fetchTrigger]);

  useEffect(() => {
    if (isUserAuth) fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isUserAuth]);

  return { requests, friends, answerFriendship };
}
