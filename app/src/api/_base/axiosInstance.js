import axios from "axios";

const API_URL ='http://localhost:8080/api'

export const axiosInstance = axios.create({
  baseURL: API_URL,
  timeout: 15000,
  withCredentials: true,
});
