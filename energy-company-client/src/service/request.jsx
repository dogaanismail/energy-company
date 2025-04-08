import axios from 'axios';
import { API_GATEWAY_URL } from '../config/config';

export const request = async (options) => {
  
  const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };

  const header = await getAuthHeader()
  if (header) {
    headers.Authorization = header;
  }

  if (options.headers) {
    Object.assign(headers, options.headers);
  }

  const axiosInstance = axios.create({
    baseURL: API_GATEWAY_URL,
    headers,
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

async function getAuthHeader() {
  const token = localStorage.getItem('token'); 
  if (token) {
    const parsedToken = JSON.parse(token); 
    return { Authorization: `Bearer ${parsedToken}` }; 
  }
  return null; 
}