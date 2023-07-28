import { axiosInstance } from "../_base/axiosInstance"

export async function userLogin(username, password) {
  const response = await axiosInstance.post(
    "/login",
    {},
    {
      auth: {
        username,
        password,
      },
    }
  );

  return response.data;
}
