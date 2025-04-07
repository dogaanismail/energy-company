import { request } from "./request";

export const login = (loginRequest) => {
  return request({
    url: '/api/v1/authentication/login',
    method: 'POST',
    data: loginRequest, t
  });
};

export const register = (registerRequest) => {
  return request({
    url: '/api/v1/authentication/register',
    method: 'POST',
    data: registerRequest, 
  });
};

export const refreshToken = (refreshTokenRequest) => {
  return request({
    url: '/api/v1/authentication/refresh-token',
    method: 'POST',
    data: refreshTokenRequest, 
  });
};

export const logout = () => {
  return request({
    url: '/api/v1/authentication/logout',
    method: 'POST',
  });
};