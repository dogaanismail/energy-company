import { useState, useEffect } from 'react';
import { consumptionService, formatConsumptionData } from '../../services/api';
import ConsumptionChart from '../ui/ConsumptionChart';
import YearSelector from '../ui/YearSelector';
import Card from '../ui/Card';
import Alert from '../ui/Alert';
import LoadingSpinner from '../ui/LoadingSpinner';

const ConsumptionOverview = () => {

  const [formattedData, setFormattedData] = useState<{ month: number; consumption: number; price: number; cost: number }[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [year, setYear] = useState<number>(new Date().getFullYear());

  useEffect(() => {
    const fetchConsumptionData = async () => {
      try {
        setLoading(true);
        const data = await consumptionService.getConsumptionForAllPoints(year);
        
        const formatted = formatConsumptionData(data);
        setFormattedData(formatted);
        
        setError(null)
      } catch (err: any) {
        console.error('Error fetching consumption data:', err);
        setError(err.message || 'Failed to fetch consumption data');
      } finally {
        setLoading(false);
      }
    };

    fetchConsumptionData();
  }, [year]);

  const handleYearChange = (newYear: number) => {
    setYear(newYear);
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="text-xl font-bold text-gray-900">Consumption Overview</h2>
        <YearSelector currentYear={year} onChange={handleYearChange} />
      </div>

      {error && <Alert type="error" message={error} onClose={() => setError(null)} />}

      {formattedData.length === 0 ? (
        <Card>
          <div className="text-center py-8">
            <p className="text-gray-500">No consumption data available for {year}.</p>
          </div>
        </Card>
      ) : (
        <ConsumptionChart data={formattedData} year={year} />
      )}
    </div>
  );
};

export default ConsumptionOverview;