import { userLogout } from "../../api/AUTH/logout";
import { useGlobalMessage } from "../../context/message";
import { useGlobalUser } from "../../context/user";

export function useLogout() {
  const [, setModal] = useGlobalMessage();
  const [, setUser] = useGlobalUser();

  const logout = async () => {
    try {
      await userLogout();
      setUser(null);
      localStorage.removeItem("user");
      localStorage.removeItem("activeFriend")
      setModal("Logout realizado com sucesso.", "message", true);
    } catch (error) {
      setModal("Erro ao realizar logout.", "error", true);
    }
  };

  return { logout };
}
