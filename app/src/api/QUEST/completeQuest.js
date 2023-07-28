import { axiosInstance } from "../_base/axiosInstance";

export async function completeQuest(questId) {
  const response = await axiosInstance.post("/quests", {
    id: questId,
  });

  return response.data;
}
