import { axiosInstance } from "../_base/axiosInstance"

export async function fetchQuests() {
  const response = await axiosInstance.get("/quests?sort=recurring");

  return response.data.content;
}
