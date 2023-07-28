import { axiosInstance } from "../_base/axiosInstance"

export async function countNewMessages(senderId, receiverId) {
  const response = await axiosInstance.get(`/messages/${senderId}/${receiverId}/count`);

  return response.data;
}
