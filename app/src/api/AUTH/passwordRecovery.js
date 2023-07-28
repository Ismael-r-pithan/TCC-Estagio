import { axiosInstance } from "../_base/axiosInstance"

export async function passwordRecovery( email ) {
  const response = await axiosInstance.post("/forgot-password", {
    email: email
  });

  return response.data;
}
