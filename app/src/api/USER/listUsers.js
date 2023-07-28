import { axiosInstance } from "../_base/axiosInstance"

export async function fetchUsers(searchInput) {
  const response = await axiosInstance.get(`/users?search=${searchInput}`);

  return response.data.content;
}
