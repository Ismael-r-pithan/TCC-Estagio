import { useEffect, useState } from "react";
import { requestFriendship } from "../../api/USER/askFriendship";
import { fetchFriends } from "../../api/USER/friendlist";
import { useGlobalMessage } from "../../context/message";

export function useRelationship(id) {
  const [friendshipStatus, setFriendshipStatus] = useState();
  const [myFriends, setMyFriends] = useState();
  const [, setModal] = useGlobalMessage();

  const fetchData = async () => {
    try {
      const friendsList = await fetchFriends();
      setMyFriends(friendsList.content);
    } catch (error) {
      setModal(
        "Erro ao requisitar informações sobre amizade, faça o login novamente",
        "error",
        true
      );
    }
  };

  async function askNewFriendship() {
    try {
      await requestFriendship(id);
      setModal("Pedido de amizade enviado com sucesso.", "message", true);
    } catch (error) {
      setModal(
        `Erro ao solicitar amizade: ${error.response.data.message}`,
        "error",
        true
      );
    }
  }

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    if (myFriends) {
      const friendship = myFriends.find(
        (friend) => friend.idFriend === parseInt(id)
      );
      friendship
        ? setFriendshipStatus("AMIGOS")
        : setFriendshipStatus("DESCONHECIDOS");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [myFriends]);

  return { friendshipStatus, askNewFriendship };
}
