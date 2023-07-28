import { axiosInstance } from "../_base/axiosInstance"

export async function changePassword( pswd,token ) {
  const response = await axiosInstance.post(`/reset-password?token=${token}`, {
    password: pswd
  });

  return response.data;
}
