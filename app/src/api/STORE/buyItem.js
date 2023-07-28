import { axiosInstance } from "../_base/axiosInstance"

export async function buyItem( itemId ) {
  const response = await axiosInstance.post("/items/buy", {
    id: itemId
  });

  return response.data;
}
