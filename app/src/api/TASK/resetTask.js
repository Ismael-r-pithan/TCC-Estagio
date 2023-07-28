import { axiosInstance } from "../_base/axiosInstance";

export async function resetTask(taskId) {
  return await axiosInstance.put(`/tasks/${taskId}/reset-finish`);
}
