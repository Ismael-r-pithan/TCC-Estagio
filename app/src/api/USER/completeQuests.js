import { axiosInstance } from "../_base/axiosInstance"

export async function fetchCompleteQuests() {
  const response = await axiosInstance.get("/me/quests");

  return response.data.content;
}
