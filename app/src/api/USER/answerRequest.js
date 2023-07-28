import { axiosInstance } from "../_base/axiosInstance"

export async function answerRequest(answer, idRequest) {
  const response = await axiosInstance.put("/me/friendships/requests", {
    response: answer,
    idRequest: idRequest
  });

  return response.data;
}
