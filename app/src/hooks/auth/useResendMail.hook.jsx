import { useState } from "react";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";
import { resendEmail } from "../../api/AUTH/activationEmail";

export function useResendMail() {
  const [email, setEmail] = useState("");
  const [enableResend, setEnableResend] = useState(false);
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
    setEnableResend(isEmailValid());
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      setLoading(true);
      await resendEmail(email);
      setModal("Um novo e-mail foi enviado para você!", "message", true);
    } catch (error) {
      setModal(error.response.data.erro, "error", true);
    } finally {
      setLoading(false);
    }
  }

  return {
    email,
    handleChange,
    handleSubmit,
    enableResend,
    touched,
    handleBlur,
  };
}
