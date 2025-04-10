import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';
import { meteringPointsService } from '../services/api';
import Card from '../components/ui/Card';
import Alert from '../components/ui/Alert';
import LoadingSpinner from '../components/ui/LoadingSpinner';
import { LightningBoltIcon, LocationMarkerIcon } from '@heroicons/react/outline';

interface MeteringPoint {
  id: string;
  address: string;
  totalConsumption?: number;
  lastMonthConsumption?: number;
}

const Dashboard = () => {
  const { user } = useAuth();
  const [meteringPoints, setMeteringPoints] = useState<MeteringPoint[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchMeteringPoints = async () => {
      try {
        setLoading(true);
        const data = await meteringPointsService.getUserMeteringPoints();
        setMeteringPoints(data);
        setError(null);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Failed to fetch metering points');
        console.error('Error fetching metering points:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchMeteringPoints();
  }, []);

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="mt-1 text-sm text-gray-500">
          Welcome back, {user?.name}
        </p>
      </div>

      {error && <Alert type="error" message={error} onClose={() => setError(null)} />}

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
            {meteringPoints.map((point) => (
              <Link key={point.id} to={`/metering-points/${point.id}`}>
                <Card className="h-full hover:shadow-md transition-shadow cursor-pointer">
                  <div className="flex items-start">
                    <div className="h-10 w-10 rounded-full bg-primary-100 flex items-center justify-center">
                      <LightningBoltIcon className="h-6 w-6 text-primary-600" />
                    </div>
                    <div className="ml-4">
                      <div className="flex items-center">
                        <LocationMarkerIcon className="h-4 w-4 text-gray-400 mr-1" />
                        <h3 className="text-lg font-medium text-gray-900 mb-1">{point.address}</h3>
                      </div>
                      {point.totalConsumption !== undefined && (
                        <p className="text-gray-500">
                          Total: <span className="font-medium">{point.totalConsumption.toFixed(2)} kWh</span>
                        </p>
                      )}
                      {point.lastMonthConsumption !== undefined && (
                        <p className="text-gray-500">
                          Last Month: <span className="font-medium">{point.lastMonthConsumption.toFixed(2)} kWh</span>
                        </p>
                      )}
                    </div>
                  </div>
                </Card>
              </Link>
            ))}
          </div>
        )}

        <div className="mt-4">
          <Link to="/metering-points" className="text-primary-600 hover:text-primary-700 font-medium">
            View all metering points →
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Dashboard