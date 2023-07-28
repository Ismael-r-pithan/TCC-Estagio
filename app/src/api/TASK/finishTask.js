import { axiosInstance } from "../_base/axiosInstance";

export async function finishTask(taskId) {
  return await axiosInstance.put(`/tasks/${taskId}/finish`);
}
