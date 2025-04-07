import axios from 'axios';
import { API_GATEWAY_URL } from '../config/config';

export const request = (options) => {
  const tokenValue = localStorage.getItem('ACCESS_TOKEN');

  const axiosInstance = axios.create({
    baseURL: API_GATEWAY_URL,
    headers: {
      'Content-Type': 'application/json',
      ...(tokenValue && { Authorization: `Bearer ${tokenValue}` }),
    },
  });

  return axiosInstance(options)
    .then((response) => response.data)
    .catch((error) => {
      if (error.response) {
        return Promise.reject(error.response.data);
      }
      return Promise.reject({ message: error.message });
    });
};