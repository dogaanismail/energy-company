import { request } from "./request";

export const getConsumptions = (customerId, meteringPointId, year) => {
  return request({
    url: '/api/v1/consumptions/customer',
    method: 'GET',
    params: {
      customerId,
      meteringPointId,
      year,
    },
  });
};