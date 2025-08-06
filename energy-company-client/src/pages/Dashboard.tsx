import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';
import { meteringPointsService } from '../services/api';
import Card from '../components/ui/Card';
import Alert from '../components/ui/Alert';
import LoadingSpinner from '../components/ui/LoadingSpinner';
import ConsumptionOverview from '../components/dashboard/ConsumptionOverview';
import { MeteringPoint } from '../models/models';

const Dashboard = () => {
  const { customer } = useAuth();
  const [meteringPoints, setMeteringPoints] = useState<MeteringPoint[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
 
        const meteringPoints = await meteringPointsService.getCustomerMeteringPoints(customer?.id || '');
        setMeteringPoints(meteringPoints);
                
        setError(null);
      } catch (err: unknown) {
        const error = err as { response?: { data?: { message?: string } } };
        setError(error.response?.data?.message || 'Failed to fetch data');
        console.error('Error fetching data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [customer?.id]);

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="mt-1 text-sm text-gray-500">
          Welcome back, {customer?.firstName || customer?.email || 'User'}
        </p>
      </div>

      {error && <Alert type="error" message={error} onClose={() => setError(null)} />}

      <div className="bg-white shadow rounded-lg p-6">
        <ConsumptionOverview />
      </div>

      <div>
        <h2 className="text-lg font-medium text-gray-900 mb-3">Your Metering Points</h2>
        
        {meteringPoints.length === 0 ? (
          <Card>
            <div className="text-center py-4">
              <p className="text-gray-500">No metering points found.</p>
            </div>
          </Card>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {meteringPoints.map((point) => {
              return (
                <Link key={point.meteringPointId} to={`/metering-points/${point.meteringPointId}`}>
                  <Card className="h-full hover:shadow-md transition-shadow cursor-pointer">
                    <div className="flex items-start">
                      <div className="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center">
                        <span className="text-green-600 font-bold">⚡</span>
                      </div>
                      <div className="ml-4">
                        <div className="flex items-center">
                          <span className="text-gray-400 mr-1">📍</span>
                          <h3 className="text-lg font-medium text-gray-900 mb-1">{point.address}</h3>
                        </div>
                      </div>
                    </div>
                  </Card>
                </Link>
              );
            })}
          </div>
        )}

        <div className="mt-4">
          <Link to="/metering-points" className="text-green-600 hover:text-green-700 font-medium">
            View all metering points →
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;