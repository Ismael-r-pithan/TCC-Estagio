import { axiosInstance } from "../_base/axiosInstance"

export async function userLogout() {
  const response = await axiosInstance.get("/logout");

  return response.data;
}
