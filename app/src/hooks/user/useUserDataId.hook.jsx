import { useEffect, useState } from "react";
import { fetchUserById } from "../../api/USER/fetchUserById";
import { useGlobalMessage } from "../../context/message";

export function useUserDataId(id) {
  const [data, setData] = useState();
  const [, setModal] = useGlobalMessage();

  const fetchData = async () => {
    try {
      const res = await fetchUserById(id);
      setData(res);
    } catch (error) {
      setModal(
        "Falha ao carregar dados do usuário, autenticação inválida ou ID inexistente.",
        "error",
        true
      );
    }
  };

  useEffect(() => {
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return { data };
}
