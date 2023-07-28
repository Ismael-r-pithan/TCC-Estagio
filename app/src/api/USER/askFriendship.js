import { axiosInstance } from "../_base/axiosInstance"

export async function requestFriendship(id) {
  const response = await axiosInstance.post(`/users/${id}/request-friendship`, {});

  return response.data;
}
