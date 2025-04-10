import { useState, useEffect } from 'react';
import { consumptionService, meteringPointsService } from '../../services/api';
import ConsumptionChart from '../ui/ConsumptionChart';
import YearSelector from '../ui/YearSelector';
import Card from '../ui/Card';
import Alert from '../ui/Alert';
import LoadingSpinner from '../ui/LoadingSpinner';
import { MeteringPoint, ConsumptionData } from '../../models/models';
import { useAuth } from '../../auth/AuthContext'; 

interface FormattedDataItem {
  month: number;
  consumption: number;
  price: number;
  cost: number;
}

const formatConsumptionData = (data: ConsumptionData[]): FormattedDataItem[] => {
  return data.map((item) => {
    const date = new Date(item.consumptionTime);
    return {
      month: date.getMonth() + 1, 
      consumption: parseFloat(item.amount),
      price: parseFloat(item.monthlyCostEurPerMwhWithVat) / 10, 
      cost: (parseFloat(item.amount) * parseFloat(item.monthlyCostEurPerMwhWithVat)) / 10,
    };
  });
};

const ConsumptionOverview = () => {
  const { customer } = useAuth(); 
  const customerId = customer?.id;
  
  const [meteringPoints, setMeteringPoints] = useState<MeteringPoint[]>([]);
  const [selectedMeteringPoint, setSelectedMeteringPoint] = useState<string>('');
  const [formattedData, setFormattedData] = useState<FormattedDataItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [year, setYear] = useState(new Date().getFullYear());


  useEffect(() => {
    const fetchMeteringPoints = async () => {
      if (!customerId) return;
      
      try {
        setLoading(true); 
        const points = await meteringPointsService.getCustomerMeteringPoints(customerId);
        setMeteringPoints(points);
        
        if (points.length > 0) {
          setSelectedMeteringPoint(points[0].meteringPointId);
        }
        
        setError(null);
      } catch (err:any) {
        console.error('Error fetching metering points:', err);
        setError(err.message || 'Failed to fetch metering points');
        setMeteringPoints([]);
      } finally {
        setLoading(false);
      }
    };

    fetchMeteringPoints();
  }, [customerId]);

  useEffect(() => {
    const fetchConsumptionData = async () => {
      if (!customerId || !selectedMeteringPoint) {
        setFormattedData([]);
        return;
      }

      try {
        setLoading(true);
        
        const data = await consumptionService.getConsumptionByMeteringPoint(
          customerId,
          selectedMeteringPoint,
          year
        );
        
        const formatted = formatConsumptionData(data);
        setFormattedData(formatted);
        
        setError(null);
      } catch (err: any) {
        console.error('Error fetching consumption data:', err);
        setError(err.message || 'Failed to fetch consumption data');
        setFormattedData([]);
      } finally {
        setLoading(false);
      }
    };

    if (selectedMeteringPoint) {
      fetchConsumptionData();
    }
  }, [selectedMeteringPoint, year, customerId]);

  const handleYearChange = (newYear: number) => {
    setYear(newYear);
  };

  const handleMeteringPointChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedMeteringPoint(event.target.value);
  };

  if (loading && meteringPoints.length === 0) {
    return <LoadingSpinner />;
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-xl font-bold text-gray-900">Consumption Overview</h2>
        <div className="flex space-x-4">
          {meteringPoints.length > 0 && (
            <select
              value={selectedMeteringPoint || ''}
              onChange={handleMeteringPointChange}
              className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
            >
              {meteringPoints.map((point) => (
                <option key={point.meteringPointId} value={point.meteringPointId}>
                  {point.address}
                </option>
              ))}
            </select>
          )}
          <YearSelector currentYear={year} onChange={handleYearChange} />
        </div>
      </div>

      {error && <Alert type="error" message={error} onClose={() => setError(null)} />}

      {meteringPoints.length === 0 ? (
        <Card>
          <div className="text-center py-8">
            <p className="text-gray-500">No metering points available for your account.</p>
          </div>
        </Card>
      ) : formattedData.length === 0 ? (
        <Card>
          <div className="text-center py-8">
            <p className="text-gray-500">No consumption data available for the selected metering point in {year}.</p>
          </div>
        </Card>
      ) : (
        <ConsumptionChart data={formattedData} year={year} />
      )}
    </div>
  );
};

export default ConsumptionOverview;