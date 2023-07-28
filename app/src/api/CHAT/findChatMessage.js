import { axiosInstance } from "../_base/axiosInstance"

export async function findChatMessage(id) {
  const response = await axiosInstance.get(`/messages/${id}`);

  return response.data;
}
