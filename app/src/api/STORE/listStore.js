import { axiosInstance } from "../_base/axiosInstance"

export async function fetchItems() {
  const response = await axiosInstance.get("/items?sort=id&size=30");

  return response.data;
}
