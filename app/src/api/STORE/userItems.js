import { axiosInstance } from "../_base/axiosInstance"

export async function fetchUserItems() {
  const response = await axiosInstance.get("/me/items");

  return response.data;
}
