import { axiosInstance } from "../_base/axiosInstance"

export async function resendEmail( email ) {
  const response = await axiosInstance.post(`/activate-account`, {
    email: email
  });

  return response.data;
}
