import { useState, useEffect } from 'react';
import { useAuth } from '../auth/AuthContext';
import { meteringPointsService } from '../services/api';
import { MeteringPoint } from '../models/models';
import LoadingSpinner from '../components/ui/LoadingSpinner';

const MeteringPoints = () => {
  const { customer } = useAuth();
  const [meteringPoints, setMeteringPoints] = useState<MeteringPoint[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const pointsData = await meteringPointsService.getCustomerMeteringPoints(customer?.id || '');
        setMeteringPoints(pointsData);
      } catch (err: unknown) {
        console.error('Error fetching data:', err);
        const error = err as { message?: string };
        setError(error.message || 'Failed to fetch metering points');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [customer?.id]);

  if (loading) {
    return <LoadingSpinner />;
  }

  if (error) {
    return <div className="text-red-600 p-4">Error: {error}</div>;
  }

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Metering Points</h1>
      
      {meteringPoints.length === 0 ? (
        <div className="bg-white shadow rounded-lg p-6 text-center">
          <p className="text-gray-500">No metering points found.</p>
        </div>
      ) : (
        <div className="bg-white shadow rounded-lg overflow-hidden">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Address
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {meteringPoints.map((point) => (
                <tr key={point.meteringPointId}>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="h-8 w-8 rounded-full bg-green-100 flex items-center justify-center text-green-600 font-bold">
                        ⚡
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-gray-900">{point.address}</div>
                      </div>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default MeteringPoints;