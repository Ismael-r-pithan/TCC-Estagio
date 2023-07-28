import { useState } from "react";
import { fetchUsers } from "../../api/USER/listUsers";

export function useUsers() {
  const [searchInput, setSearchInput] = useState('');
  const [userList, setUserList] = useState();
  const [erro, setErro] = useState();

  const fetchData = async () => {
    try {
      const data = await fetchUsers(searchInput);
      setUserList(data);
    } catch (error) {
      setErro("Falha ao carregar lista de usuários");
    }
  };

  function handleChange(event) {
    setSearchInput(event.target.value);
  }

  return { erro, userList, fetchData, handleChange, searchInput };
}
