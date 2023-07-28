import { useState } from "react";

export function useForms(formFields, onSubmit) {
  const [form, setForm] = useState(formFields);

  function handleChange(event) {
    setForm((prevForm) => ({
      ...form,
      [event.target.name]: event.target.value,
    }));
  }

  async function handleCheckboxChange (event) {
    setForm((prevForm) => ({
      ...form,
      [event.target.name]: event.target.checked,
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    onSubmit(form);
  }

  return { form, handleChange, handleSubmit, handleCheckboxChange };
}
