export interface CustomResponse<T> {
    time: number[];
    httpStatus: string;
    isSuccess: boolean;
    response: T;
  }
  
  export interface Customer {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
    customerStatus: 'ACTIVE' | 'INACTIVE' | 'SUSPENDED';
    customerType: 'RESIDENTIAL' | 'COMMERCIAL' | 'INDUSTRIAL';
  }
  
  export interface MeteringPoint {
    id: string;
    customerId: string;
    address: string;
    status: 'ACTIVE' | 'INACTIVE';
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
  
  export interface FormattedConsumptionData {
    month: number;
    consumption: number;
    price: number;
    cost: number;
  }
  
  export interface User {
    id: string;
    username: string;
    name: string;
    email: string;
    role: string;
    createdAt: string;
  }
  