import { useState } from "react";
import { passwordRecovery } from "../../api/AUTH/passwordRecovery";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

export function usePasswordRecovery() {
  const [email, setEmail] = useState("");
  const [enableRecovery, setEnableRecovery] = useState(false);
  const [touched, setTouched] = useState(false);

  const [, setModal] = useGlobalMessage();
  const { setLoading } = useLoading();

  function handleChange(event) {
    setEmail(event.target.value);
  }

  function isEmailValid() {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  function handleBlur() {
    setTouched(true);
    setEnableRecovery(isEmailValid());
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      setLoading(true);
      const res = await passwordRecovery(email);
      setModal(res, "message", true);
    } catch (error) {
      setModal(error.response.data.message, "error", true);
    } finally {
      setLoading(false);
    }
  }

  return {
    email,
    handleChange,
    handleSubmit,
    enableRecovery,
    touched,
    handleBlur,
  };
}
