import '@testing-library/jest-dom';
import { render, screen, waitFor, act } from '@testing-library/react';
import MeteringPoints from '../pages/MeteringPoints';
import { meteringPointsService } from '../services/api';
import * as AuthContext from '../auth/AuthContext';
import { CustomerStatus, CustomerType } from '../models/models';

jest.mock('../services/api', () => ({
  meteringPointsService: {
    getCustomerMeteringPoints: jest.fn()
  }
}));

describe('MeteringPoints', () => {
  const mockCustomer = {
    id: 'customer123',
    email: 'test@example.com',
    firstName: 'John',
    lastName: 'Doe',
    customerStatus: CustomerStatus.ACTIVE,
    customerType: CustomerType.CUSTOMER
  };

  const mockMeteringPoints = [
    { meteringPointId: 'point1', customerId: 'customer123', address: 'Address 1' },
    { meteringPointId: 'point2', customerId: 'customer123', address: 'Address 2' }
  ];

  beforeEach(() => {
    jest.clearAllMocks();
    
    jest.spyOn(AuthContext, 'useAuth').mockImplementation(() => ({
      customer: mockCustomer,
      loading: false,
      error: null,
      login: jest.fn(),
      register: jest.fn(),
      logout: jest.fn(),
      refreshToken: jest.fn()
    }));

    (meteringPointsService.getCustomerMeteringPoints as jest.Mock).mockResolvedValue(mockMeteringPoints);
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('fetches and displays metering points', async () => {
    await act(async () => {
      render(<MeteringPoints />);
    });

    await waitFor(() => {
      expect(meteringPointsService.getCustomerMeteringPoints).toHaveBeenCalledWith('customer123');
    });

    await waitFor(() => {
      expect(screen.getByText('Address 1')).toBeInTheDocument();
      expect(screen.getByText('Address 2')).toBeInTheDocument();
    });
  });

  it('displays error message when API call fails', async () => {
    const errorMessage = 'Failed to fetch data';
    (meteringPointsService.getCustomerMeteringPoints as jest.Mock).mockRejectedValue(
      new Error(errorMessage)
    );

    await act(async () => {
      render(<MeteringPoints />);
    });

    await waitFor(() => {
      expect(screen.getByText(`Error: ${errorMessage}`)).toBeInTheDocument();
    });
  });

  it('displays message when no metering points are available', async () => {
    (meteringPointsService.getCustomerMeteringPoints as jest.Mock).mockResolvedValue([]);

    await act(async () => {
      render(<MeteringPoints />);
    });

    await waitFor(() => {
      expect(screen.getByText('No metering points found.')).toBeInTheDocument();
    });
  });

  it('passes correct customer id to API call', async () => {
    await act(async () => {
      render(<MeteringPoints />);
    });

    await waitFor(() => {
      expect(meteringPointsService.getCustomerMeteringPoints).toHaveBeenCalledWith('customer123');
    });
  });

  it('handles case when customer is null', async () => {
    jest.spyOn(AuthContext, 'useAuth').mockImplementation(() => ({
      customer: null,
      loading: false,
      error: null,
      login: jest.fn(),
      register: jest.fn(),
      logout: jest.fn(),
      refreshToken: jest.fn()
    }));

    await act(async () => {
      render(<MeteringPoints />);
    });

    await waitFor(() => {
      expect(meteringPointsService.getCustomerMeteringPoints).toHaveBeenCalledWith('');
    });
  });
});