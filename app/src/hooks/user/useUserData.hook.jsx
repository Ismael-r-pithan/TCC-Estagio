import { useEffect, useState } from "react";
import { fetchAuthenticatedUserData } from "../../api/USER/me";
import { useGlobalMessage } from "../../context/message";

export function useUserData() {
  const [data, setData] = useState();
  const [, setModal] = useGlobalMessage();

  const fetchData = async () => {
    try {
      const res = await fetchAuthenticatedUserData();
      setData(res);
    } catch (error) {
      setModal(
        "Falha ao carregar dados do usuário, realize o login novamente.",
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
