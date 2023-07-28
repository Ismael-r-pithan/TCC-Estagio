import { axiosInstance } from "../_base/axiosInstance";

export async function fetchTasks(userId, date, category, priority, page) {
  const response = await axiosInstance.get(
    `/users/${userId}/tasks?date=${date}&category=${category}&priority=${priority}&page=${page}`
  );
  return response.data.content;
}
