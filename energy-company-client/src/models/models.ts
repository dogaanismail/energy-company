export interface CustomResponse<T> {
  time: number[];
  httpStatus: string;
  isSuccess: boolean;
  response: T;
}

export enum CustomerStatus {
  ACTIVE = 'ACTIVE',
  PASSIVE = 'PASSIVE',
  SUSPENDED = 'SUSPENDED',
}

export enum CustomerType {
  CUSTOMER = 'CUSTOMER',
  ADMIN = 'ADMIN'
}

export interface Customer {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  customerStatus: CustomerStatus;
  customerType: CustomerType;
}

export interface MeteringPoint {
  meteringPointId: string;
  customerId: string;
  address: string;
}

export interface ConsumptionData {
  consumptionId: string;
  meteringPointId: string;
  customerId: string;
  address: string;
  amount: string;
  amountUnit: string;
  consumptionTime: string;
  monthlyCostCentsPerKwh: string;
  monthlyCostCentsPerKwhWithVat: string;
  monthlyCostEurPerMwh: string;
  monthlyCostEurPerMwhWithVat: string;
}

export interface TokenResponse {
  accessToken: string;
  accessTokenExpiresAt: number;
  refreshToken: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  customerType: string;
}

export interface TokenRefreshRequest {
  refreshToken: string;
}

export interface TokenInvalidateRequest {
  refreshToken: string;
}

export interface FormattedConsumptionData {
  month: number;
  consumption: number;
  price: number;
  cost: number;
}