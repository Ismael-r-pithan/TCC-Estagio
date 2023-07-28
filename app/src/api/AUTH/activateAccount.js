import { axiosInstance } from "../_base/axiosInstance"

export async function activateAccount(token, email) {
  const response = await axiosInstance.get(`/activate-account?email=${email}&token=${token}`);

  return response.data;
}
