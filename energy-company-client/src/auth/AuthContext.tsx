import React, { createContext, useState, useEffect, useContext, ReactNode } from 'react';
import { authService } from '../services/api';
import { Customer } from '../models/models';

interface AuthContextType {
  customer: Customer | null;
  loading: boolean;
  error: string | null;
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, firstName: string, lastName: string, password: string, customerType: string) => Promise<void>;
  logout: () => Promise<void>;
  refreshToken: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [customer, setCustomer] = useState<Customer | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCustomer = async () => {
      try {
        if (localStorage.getItem('accessToken')) {
          const currentCustomer = await authService.getCurrentCustomer();
          setCustomer(currentCustomer);
        }
      } catch (err) {
        console.error('Failed to fetch customer data:', err);
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
      } finally {
        setLoading(false);
      }
    };

    fetchCustomer();
  }, []);


  const login = async (email: string, password: string) => {
    setLoading(true);
    setError(null);
    try {
      const data = await authService.login(email, password);
      
      localStorage.setItem('accessToken', data.accessToken);
      if (data.refreshToken) {
        localStorage.setItem('refreshToken', data.refreshToken);
      }
      
      const currentCustomer = await authService.getCurrentCustomer();
      setCustomer(currentCustomer);
    } catch (err: unknown) {
      const error = err as { response?: { data?: { message?: string } } };
      setError(error.response?.data?.message || 'Login failed');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const register = async (email: string, firstName: string, lastName: string, password: string, customerType: string) => {
    setLoading(true);
    setError(null);
    try {
      await authService.register(email, firstName, lastName, password, customerType);
    } catch (err: unknown) {
      const error = err as { response?: { data?: { message?: string } } };
      setError(error.response?.data?.message || 'Registration failed');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    try {
      const refreshToken = localStorage.getItem('refreshToken');
      if (refreshToken) {
        await authService.logout(refreshToken);
      }
    } catch (err) {
      console.error('Error during logout:', err);
    } finally {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      setCustomer(null);
    }
  };

  const refreshToken = async () => {
    const refreshTokenValue = localStorage.getItem('refreshToken');
    if (!refreshTokenValue) return;
    
    try {
      const data = await authService.refreshToken(refreshTokenValue);
      localStorage.setItem('accessToken', data.accessToken);
      if (data.refreshToken) {
        localStorage.setItem('refreshToken', data.refreshToken);
      }
    } catch (err) {
      console.error('Error refreshing token:', err);
      await logout();
    }
  };

  return (
    <AuthContext.Provider value={{ 
      customer, 
      loading, 
      error, 
      login, 
      register, 
      logout,
      refreshToken
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};