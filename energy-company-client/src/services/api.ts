import axios from 'axios';
import {CustomResponse, ConsumptionData, MeteringPoint, Customer, TokenResponse} from '../models/models';

const API_URL = import.meta.env.VITE_API_GATEWAY_URL || 'http://localhost:4000';

const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const refreshToken = localStorage.getItem('refreshToken');
                if (refreshToken) {
                    const response = await axios.post(`${API_URL}/api/v1/authentication/customers/refresh-token`, {refreshToken});
                    const newTokens = response.data.response;

                    localStorage.setItem('accessToken', newTokens.accessToken);
                    if (newTokens.refreshToken) {
                        localStorage.setItem('refreshToken', newTokens.refreshToken);
                    }

                    originalRequest.headers.Authorization = `Bearer ${newTokens.accessToken}`;
                    return axios(originalRequest);
                }
            } catch {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');

                if (window.location.pathname !== '/login') {
                    window.location.href = '/login';
                }
            }
        }

        return Promise.reject(error);
    }
);

const extractResponseData = <T>(data: CustomResponse<T>): T => {
    if (!data.isSuccess) {
        throw new Error(data.httpStatus || 'Request failed');
    }
    return data.response;
};

export const authService = {
    login: async (email: string, password: string) => {
        const response = await api.post<CustomResponse<TokenResponse>>('/api/v1/authentication/customers/login', {
            email,
            password
        });
        return extractResponseData(response.data);
    },

    register: async (email: string, firstName: string, lastName: string, password: string, role: string) => {
        const response = await api.post<CustomResponse<void>>('/api/v1/authentication/customers/register', {
            email,
            firstName,
            lastName,
            password,
            role
        });
        return extractResponseData(response.data);
    },

    logout: async (refreshToken: string) => {
        const response = await api.post<CustomResponse<void>>('/api/v1/authentication/customers/logout', {refreshToken});
        return extractResponseData(response.data);
    },

    refreshToken: async (refreshToken: string) => {
        const response = await api.post<CustomResponse<TokenResponse>>('/api/v1/authentication/customers/refresh-token', {refreshToken});
        return extractResponseData(response.data);
    },

    getCurrentCustomer: async () => {
        const response = await api.get<CustomResponse<Customer>>('/api/v1/customers/current-customer');
        return extractResponseData(response.data);
    },
};

export const meteringPointsService = {
    getCustomerMeteringPoints: async (customerId: string) => {
        const response = await api.get<CustomResponse<MeteringPoint[]>>('/api/v1/consumptions/metering-points/customer', {
            params: {customerId},
        });
        return extractResponseData(response.data);
    }
};

export const consumptionService = {
    getConsumptionByMeteringPoint: async (customerId: string, meteringPointId: string, year: number) => {
        const response = await api.get<CustomResponse<ConsumptionData[]>>('/api/v1/consumptions/customer', {
            params: {customerId, meteringPointId, year},
        });
        return extractResponseData(response.data);
    },
};