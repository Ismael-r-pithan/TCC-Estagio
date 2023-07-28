import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAuthenticatedUserData } from "../../api/USER/me";
import { useGlobalMessage } from "../../context/message";
import { useGlobalUser } from "../../context/user";

export function useValidateUser() {
  const [isUserAuth, setIsUserAuth] = useState(false);
  const [, setUser] = useGlobalUser();
  const [, setModal] = useGlobalMessage();
  const navigate = useNavigate();

  const fetchUser = async () => {
    try {
      await fetchAuthenticatedUserData();
      setIsUserAuth(true);
    } catch (error) {
      setModal(
        "Sessão expirou ou acesso não autorizado, realize login novamente.",
        "error",
        true
      );
      setUser(null);
      localStorage.removeItem("user");
      localStorage.removeItem("activeFriend");
      navigate("/");
    }
  };

  useEffect(() => {
    fetchUser();
  }, []);

  return { isUserAuth };
}
