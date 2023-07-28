import { axiosInstance } from "../_base/axiosInstance"

export async function findChatMessages(senderId, receiverId) {
  const response = await axiosInstance.get(`/messages/${senderId}/${receiverId}`);

  return response.data;
}
