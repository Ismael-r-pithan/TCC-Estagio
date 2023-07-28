import { axiosInstance } from "../_base/axiosInstance";

export async function changeAvatar(avatarId) {
  const response = await axiosInstance.put('/me/avatar', {
    avatarId: avatarId
  });

  return response.data;
}
