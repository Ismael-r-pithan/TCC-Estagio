import { axiosInstance } from "../_base/axiosInstance";

export async function createTask(
  {name,
  description,
  scheduledDate,
  startTime,
  endTime,
  recurring,
  visibility,
  category,
  priority}
) {
  return await axiosInstance.post(`/tasks`, {
    name:name,
    description:description,
    scheduledDate:scheduledDate,
    startTime:startTime,
    endTime:endTime,
    recurring:recurring,
    visibility:visibility,
    category:category,
    priority:priority,
  });
}
