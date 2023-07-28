import { axiosInstance } from "../_base/axiosInstance"

export async function fetchFriends() {
  const response = await axiosInstance.get("/me/friendships");

  return response.data;
}
