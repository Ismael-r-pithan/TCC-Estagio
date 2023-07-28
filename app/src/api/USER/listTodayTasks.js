import { axiosInstance } from "../_base/axiosInstance"

export async function fetchTodayTasks(userId, date) {
  const response = await axiosInstance.get(`/users/${userId}/tasks?date=${date}`);

  return response.data.content;
}
