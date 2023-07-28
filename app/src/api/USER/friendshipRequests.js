import { axiosInstance } from "../_base/axiosInstance"

export async function fetchRequests() {
  const response = await axiosInstance.get("/me/friendships/requests");

  return response.data;
}
