import { axiosInstance } from "../_base/axiosInstance";

export async function deleteTask(taskId) {
  return await axiosInstance.delete(`/tasks/${taskId}`);
}
