import { axiosInstance } from "../_base/axiosInstance"

export async function checkQuestStatus(idQuest) {
  const response = await axiosInstance.get(`/me/quests/${idQuest}/status`);

  return response.data;
}
