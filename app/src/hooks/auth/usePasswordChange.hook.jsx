import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { changePassword } from "../../api/AUTH/changePassword";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";
export function usePasswordChange() {
  const [form, setForm] = useState({
    password: "",
    confirmPassword: "",
  });

  const [passwordValid, setPasswordValid] = useState(false);
  const [token, setToken] = useState();
  const [, setModal] = useGlobalMessage();
  const { setLoading } = useLoading();
  const location = useLocation();
  const navigate = useNavigate();

  function parseURL(location) {
    const regex = /(?<=\?token=)[^&]+/;
    const match = location.match(regex);
    setToken(match[0]);
  }

  function handleChange(event) {
    setForm({ ...form, [event.target.name]: event.target.value });
  }

  function handleBlur() {
    if (form.password === form.confirmPassword) setPasswordValid(true);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      setLoading(true);
      await changePassword(form.password, token);
      navigate('/')
      setModal("Senha alterada com sucesso!", "message", true);
    } catch (error) {
      setModal("Requisição inválida.", "error", true);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    parseURL(location.search);
  }, [location.search]);

  return { form, handleChange, handleBlur, passwordValid, handleSubmit };
}
