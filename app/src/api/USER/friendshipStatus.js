import { axiosInstance } from "../_base/axiosInstance"

export async function fetchFriendshipStatus(idRequest) {
  const response = await axiosInstance.get(`/me/friendships/${idRequest}/status`);

  return response.data;
}
