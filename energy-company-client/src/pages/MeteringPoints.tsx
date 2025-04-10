import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { meteringPointsService, consumptionService } from '../services/api';
import { MeteringPoint, ConsumptionData } from '../models/models';
import Alert from '../components/ui/Alert';
import LoadingSpinner from '../components/ui/LoadingSpinner';

// Define types for filtering and sorting
type SortField = 'address' | 'totalConsumption' | 'lastMonthConsumption';
type SortDirection = 'asc' | 'desc';

const MeteringPoints = () => {
  const [meteringPoints, setMeteringPoints] = useState<MeteringPoint[]>([]);
  const [consumptionData, setConsumptionData] = useState<ConsumptionData[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [sortField, setSortField] = useState<SortField>('address');
  const [sortDirection, setSortDirection] = useState<SortDirection>('asc');
  
  // Calculate the current year
  const currentYear = new Date().getFullYear();

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        
        // Fetch metering points
        const pointsData = await meteringPointsService.getUserMeteringPoints();
        setMeteringPoints(pointsData);
        
        // Fetch consumption data for all points
        const consumptionData = await consumptionService.getConsumptionForAllPoints(currentYear);
        setConsumptionData(consumptionData);
        
        setError(null);
      } catch (err: any) {
        console.error('Error fetching data:', err);
        setError(err.message || 'Failed to fetch data');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // Calculate last month's consumption and total consumption for each metering point
  const getMeteringPointStats = (meteringPointId: string) => {
    const pointConsumption = consumptionData.filter(item => item.meteringPointId === meteringPointId);
    
    // Sort by date
    pointConsumption.sort((a, b) => 
      new Date(a.consumptionTime).getTime() - new Date(b.consumptionTime).getTime()
    );
    
    // Calculate total consumption
    const totalConsumption = pointConsumption.reduce(
      (sum, item) => sum + parseFloat(item.amount), 
      0
    );
    
    // Get last month's consumption (last item in the sorted array)
    const lastMonthConsumption = pointConsumption.length > 0 
      ? parseFloat(pointConsumption[pointConsumption.length - 1].amount) 
      : 0;
    
    return {
      totalConsumption,
      lastMonthConsumption
    };
  };

  // Handle sorting
  const handleSort = (field: SortField) => {
    if (field === sortField) {
      // Toggle direction if clicking on the same field
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      // Default to ascending order for a new field
      setSortField(field);
      setSortDirection('asc');
    }
  };

  // Filter and sort metering points
  const filteredAndSortedPoints = meteringPoints
    .filter(point => 
      point.address.toLowerCase().includes(searchTerm.toLowerCase())
    )
    .map(point => {
      const stats = getMeteringPointStats(point.id);
      return {
        ...point,
        ...stats
      };
    })
    .sort((a, b) => {
      if (sortField === 'address') {
        return sortDirection === 'asc' 
          ? a.address.localeCompare(b.address)
          : b.address.localeCompare(a.address);
      } else if (sortField === 'totalConsumption') {
        return sortDirection === 'asc'
          ? a.totalConsumption - b.totalConsumption
          : b.totalConsumption - a.totalConsumption;
      } else if (sortField === 'lastMonthConsumption') {
        return sortDirection === 'asc'
          ? a.lastMonthConsumption - b.lastMonthConsumption
          : b.lastMonthConsumption - a.lastMonthConsumption;
      }
      return 0;
    });

  // Define sort icon based on current sort state
  const getSortIcon = (field: SortField) => {
    if (field !== sortField) return '⇅';
    return sortDirection === 'asc' ? '↑' : '↓';
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Metering Points</h1>
        <p className="mt-1 text-sm text-gray-500">
          View and manage all your metering points
        </p>
      </div>

      {error && <Alert type="error" message={error} onClose={() => setError(null)} />}

      <div className="bg-white shadow rounded-lg overflow-hidden">
        <div className="p-4 border-b border-gray-200">
          <div className="flex flex-col sm:flex-row justify-between items-center space-y-3 sm:space-y-0">
            <div className="w-full sm:w-auto">
              <input
                type="text"
                placeholder="Search by address..."
                className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500 sm:text-sm"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="text-sm text-gray-500">
              Showing {filteredAndSortedPoints.length} of {meteringPoints.length} metering points
            </div>
          </div>
        </div>

        {filteredAndSortedPoints.length === 0 ? (
          <div className="text-center py-8">
            <p className="text-gray-500">No metering points found.</p>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                    onClick={() => handleSort('address')}
                  >
                    Address {getSortIcon('address')}
                  </th>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                    onClick={() => handleSort('totalConsumption')}
                  >
                    Total Consumption {getSortIcon('totalConsumption')}
                  </th>
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider cursor-pointer"
                    onClick={() => handleSort('lastMonthConsumption')}
                  >
                    Last Month {getSortIcon('lastMonthConsumption')}
                  </th>
                  <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredAndSortedPoints.map((point) => (
                  <tr key={point.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex items-center">
                        <div className="h-8 w-8 rounded-full bg-green-100 flex items-center justify-center text-green-600 font-bold">
                          ⚡
                        </div>
                        <div className="ml-4">
                          <div className="text-sm font-medium text-gray-900">{point.address}</div>
                          <div className="text-xs text-gray-500">{point.id.substring(0, 8)}...</div>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">{point.totalConsumption.toFixed(2)} kWh</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900">{point.lastMonthConsumption.toFixed(2)} kWh</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                      <Link
                        to={`/metering-points/${point.id}`}
                        className="text-green-600 hover:text-green-900"
                      >
                        View Details
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default MeteringPoints;