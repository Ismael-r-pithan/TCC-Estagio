import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { userRegister } from "../../api";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).{8,}$/;
const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;


export function useRegister() {
  const [form, setForm] = useState({
    email: "",
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [, setModal] = useGlobalMessage();
  const [emailTouched, setEmailTouched] = useState(false);
  const [pswdTouched, setPswdTouched] = useState(false);
  const [confirmPswdTouched, setConfirmPswdTouched] = useState(false);
  const [emailValid, setEmailValid] = useState(false);
  const [pswdValid, setPswdValid] = useState(false);
  const [confirmPswdValid, setConfirmPswdValid] = useState(false)
  const [enableRegister, setEnableRegister] = useState(false);
  const { setLoading } = useLoading();
  const navigate = useNavigate();

  function isEmailValid() {
    return EMAIL_REGEX.test(form.email);
  }

  function isPasswordValid(){
    return PASSWORD_REGEX.test(form.password);
  }

  function handleEmailBlur() {
    setEmailTouched(true);
    setEmailValid(isEmailValid);
  }

  function handlePasswordBlur(){
    setPswdTouched(true);
    setPswdValid(isPasswordValid())
  }

  function handleConfirmPasswordBlur() {
    setConfirmPswdTouched(true);
    setConfirmPswdValid(form.password === form.confirmPassword);
  }

  function handleChange(event) {
    setForm({ ...form, [event.target.name]: event.target.value });
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      setLoading(true);
      await userRegister(form.email, form.password, form.username);
      setModal(
        "Registro realizado com sucesso! Cheque seu e-mail para realizar a confirmação da conta.",
        "message",
        true
      );
      navigate("/");
    } catch (error) {
      setModal(error.response.data.message, "error", true);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (pswdValid && emailValid) setEnableRegister(true);
  }, [pswdValid, emailValid]);

  return {
    form,
    handleChange,
    handleSubmit,
    handleEmailBlur,
    handlePasswordBlur,
    pswdValid,
    emailValid,
    emailTouched,
    pswdTouched,
    enableRegister,
    confirmPswdTouched,
    confirmPswdValid,
    handleConfirmPasswordBlur
  };
}
