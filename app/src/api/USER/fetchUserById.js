import { axiosInstance } from "../_base/axiosInstance"

export async function fetchUserById(id) {
  const response = await axiosInstance.get(`/users/${id}`);

  return response.data;
}
