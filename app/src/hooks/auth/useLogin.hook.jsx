import {  useState } from "react";
import { userLogin } from "../../api";
import { useGlobalMessage } from "../../context/message";
import { useGlobalUser } from "../../context/user";

export function useLogin() {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });
  const [enableLogin, setEnableLogin] = useState(false);
  const [touched, setTouched] = useState(false);
  const [, setModal] = useGlobalMessage();
  const [, setUser] = useGlobalUser();

  function handleChange(event) {
    setForm({ ...form, [event.target.name]: event.target.value });
  }

  function isEmailValid() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(form.email);
  }

  function handleBlur(){
    setTouched(true);
    setEnableLogin(isEmailValid());
  }

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      const user = await userLogin(form.email, form.password);
      setUser(user);
      setModal(
        `Login realizado com sucesso, seja bem vindo ${user.username}!`,
        "message",
        true
      );
    } catch (error) {
      setModal(error.response.data.erro, "error", true);
    }
  }

  return { form, handleChange, handleSubmit,  enableLogin, touched, handleBlur };
}
