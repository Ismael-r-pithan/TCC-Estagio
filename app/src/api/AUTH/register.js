import { axiosInstance } from "../_base/axiosInstance"

export async function userRegister( email, password, username ) {
  const response = await axiosInstance.post("/users", {
    email: email,
    username: username,
    password: password
  });

  return response.data;
}
