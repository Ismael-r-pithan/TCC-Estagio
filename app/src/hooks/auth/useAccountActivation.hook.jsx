import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { activateAccount } from "../../api/AUTH/activateAccount";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

export function useAccountActivation() {
  const location = useLocation();
  const [token, setToken] = useState();
  const [email, setEmail] = useState();
  const [, setModal] = useGlobalMessage();
  const { setLoading } = useLoading();
  const navigate = useNavigate();

  function parseURL(location) {
    const regex =
      /token=([a-zA-Z0-9]+)&email=([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,})/;
    const match = location.match(regex);
    setToken(match[1]);
    setEmail(match[2]);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      setLoading(true);
      await activateAccount(token, email);
      setModal(
        "Sua conta foi ativada com sucesso! Você poderá realizar o login agora.",
        "message",
        true
      );
      navigate("/");
    } catch (error) {
      setModal(error.response.data.erro, "error", true);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    parseURL(location.search);
  }, [location.search]);

  return { handleSubmit };
}
