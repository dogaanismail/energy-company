import axios from 'axios';
import { CustomResponse, ConsumptionData, MeteringPoint, User } from '../models/models';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:5000/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      if (window.location.pathname !== '/login') {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

const extractResponseData = <T>(data: CustomResponse<T>): T => {
  if (!data.isSuccess || data.httpStatus !== 'OK') {
    throw new Error(data.httpStatus || 'Request failed');
  }
  return data.response;
};

export const authService = {
  login: async (username: string, password: string) => {
    const response = await api.post<CustomResponse<{ token: string; user: User }>>('/auth/login', { username, password });
    const data = extractResponseData(response.data);
    if (data.token) {
      localStorage.setItem('token', data.token);
    }
    return data;
  },
  
  register: async (username: string, password: string, email: string, name: string) => {
    const response = await api.post<CustomResponse<any>>('/auth/register', { username, password, email, name });
    return extractResponseData(response.data);
  },
  
  logout: () => {
    localStorage.removeItem('token');
  },
  
  getCurrentUser: async () => {
    const response = await api.get<CustomResponse<User>>('/auth/me');
    return extractResponseData(response.data);
  },

  updateProfile: async (data: Partial<{name: string; email: string}>) => {
    const response = await api.put<CustomResponse<User>>('/auth/profile', data);
    return extractResponseData(response.data);
  },

  updatePassword: async (currentPassword: string, newPassword: string) => {
    const response = await api.put<CustomResponse<any>>('/auth/password', {
      currentPassword,
      newPassword
    });
    return extractResponseData(response.data);
  }
};

export const meteringPointsService = {
  getUserMeteringPoints: async () => {
    const response = await api.get<CustomResponse<MeteringPoint[]>>('/metering-points');
    return extractResponseData(response.data);
  },
  
  getMeteringPointById: async (id: string) => {
    const response = await api.get<CustomResponse<MeteringPoint>>(`/metering-points/${id}`);
    return extractResponseData(response.data);
  },
};

export const consumptionService = {
  getConsumptionByMeteringPoint: async (meteringPointId: string, year: number) => {
    const response = await api.get<CustomResponse<ConsumptionData[]>>(`/consumption/${meteringPointId}`, {
      params: { year },
    });
    return extractResponseData(response.data);
  },
  
  getConsumptionForAllPoints: async (year: number) => {
    const response = await api.get<CustomResponse<ConsumptionData[]>>('/consumption', {
      params: { year },
    });
    return extractResponseData(response.data);
  },
  
  getMarketPrices: async (startDate: string, endDate: string) => {
    const response = await api.get<CustomResponse<any>>('/market-prices', {
      params: { startDate, endDate },
    });
    return extractResponseData(response.data);
  },
};

export const formatConsumptionData = (data: any[]): { month: number; consumption: number; price: number; cost: number }[] => {
  return data.map(item => ({
    month: item.month,
    consumption: item.consumption,
    price: item.price,
    cost: item.cost,
  }));
};

export default api;