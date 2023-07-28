import { axiosInstance } from "../_base/axiosInstance"

export async function fetchAuthenticatedUserData() {
  const response = await axiosInstance.get("/me");

  return response.data;
}
