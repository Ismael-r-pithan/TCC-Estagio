import { axiosInstance } from "../_base/axiosInstance";

export async function changeBanner(bannerId) {
  const response = await axiosInstance.put('/me/banner', {
    bannerId: bannerId
  });

  return response.data;
}
