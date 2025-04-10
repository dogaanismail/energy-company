import { useState } from 'react';
import { 
  LineChart, 
  Line, 
  XAxis, 
  YAxis, 
  CartesianGrid, 
  Tooltip, 
  Legend, 
  ResponsiveContainer,
  BarChart,
  Bar,
  ComposedChart,
  Area
} from 'recharts';

const monthNames = [
  'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
  'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
];

interface ConsumptionChartProps {
  data: {
    month: number;
    consumption: number;
    price: number;
    cost: number;
  }[];
  year: number;
}

type ChartType = 'line' | 'bar' | 'composed';

const ConsumptionChart = ({ data, year }: ConsumptionChartProps) => {
  const [chartType, setChartType] = useState<ChartType>('line');
  
  const formattedData = data.map(item => ({
    ...item,
    month: monthNames[item.month - 1],
  }));
  
  const totalConsumption = data.reduce((sum, item) => sum + item.consumption, 0);
  const totalCost = data.reduce((sum, item) => sum + item.cost, 0);
  const avgPrice = data.reduce((sum, item) => sum + item.price, 0) / data.length;

  return (
    <div className="space-y-4">
      <div className="flex flex-wrap gap-4 mb-4">
        <div className="flex-1 min-w-[200px] bg-white p-4 rounded-lg shadow border border-gray-100">
          <h3 className="text-sm font-medium text-gray-500">Total Consumption</h3>
          <p className="text-2xl font-semibold text-gray-900">{totalConsumption.toFixed(2)} kWh</p>
        </div>
        <div className="flex-1 min-w-[200px] bg-white p-4 rounded-lg shadow border border-gray-100">
          <h3 className="text-sm font-medium text-gray-500">Total Cost</h3>
          <p className="text-2xl font-semibold text-gray-900">{totalCost.toFixed(2)} €</p>
        </div>
        <div className="flex-1 min-w-[200px] bg-white p-4 rounded-lg shadow border border-gray-100">
          <h3 className="text-sm font-medium text-gray-500">Average Price</h3>
          <p className="text-2xl font-semibold text-gray-900">{avgPrice.toFixed(4)} €/kWh</p>
        </div>
      </div>

      <div className="bg-white p-4 rounded-lg shadow border border-gray-100">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-medium text-gray-900">Energy Consumption - {year}</h2>
          <div className="flex space-x-2">
            <button
              onClick={() => setChartType('line')}
              className={`px-3 py-1 text-sm rounded ${
                chartType === 'line' 
                  ? 'bg-primary-100 text-primary-800' 
                  : 'bg-gray-100 text-gray-800 hover:bg-gray-200'
              }`}
            >
              Line
            </button>
            <button
              onClick={() => setChartType('bar')}
              className={`px-3 py-1 text-sm rounded ${
                chartType === 'bar' 
                  ? 'bg-primary-100 text-primary-800' 
                  : 'bg-gray-100 text-gray-800 hover:bg-gray-200'
              }`}
            >
              Bar
            </button>
            <button
              onClick={() => setChartType('composed')}
              className={`px-3 py-1 text-sm rounded ${
                chartType === 'composed' 
                  ? 'bg-primary-100 text-primary-800' 
                  : 'bg-gray-100 text-gray-800 hover:bg-gray-200'
              }`}
            >
              Combined
            </button>
          </div>
        </div>
        
        <div className="h-80">
          <ResponsiveContainer width="100%" height="100%">
            {chartType === 'line' ? (
              <LineChart data={formattedData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis yAxisId="left" orientation="left" stroke="#16a34a" />
                <YAxis yAxisId="right" orientation="right" stroke="#6366f1" />
                <Tooltip />
                <Legend />
                <Line
                  yAxisId="left"
                  type="monotone"
                  dataKey="consumption"
                  name="Consumption (kWh)"
                  stroke="#16a34a"
                  activeDot={{ r: 8 }}
                />
                <Line
                  yAxisId="right"
                  type="monotone"
                  dataKey="cost"
                  name="Cost (€)"
                  stroke="#6366f1"
                />
              </LineChart>
            ) : chartType === 'bar' ? (
              <BarChart data={formattedData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis yAxisId="left" orientation="left" stroke="#16a34a" />
                <YAxis yAxisId="right" orientation="right" stroke="#6366f1" />
                <Tooltip />
                <Legend />
                <Bar
                  yAxisId="left"
                  dataKey="consumption"
                  name="Consumption (kWh)"
                  fill="#16a34a"
                />
                <Bar
                  yAxisId="right"
                  dataKey="cost"
                  name="Cost (€)"
                  fill="#6366f1"
                />
              </BarChart>
            ) : (
              <ComposedChart data={formattedData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis yAxisId="left" orientation="left" stroke="#16a34a" />
                <YAxis yAxisId="right" orientation="right" stroke="#6366f1" />
                <Tooltip />
                <Legend />
                <Bar
                  yAxisId="left"
                  dataKey="consumption"
                  name="Consumption (kWh)"
                  fill="#16a34a"
                  barSize={20}
                />
                <Line
                  yAxisId="right"
                  type="monotone"
                  dataKey="cost"
                  name="Cost (€)"
                  stroke="#6366f1"
                />
                <Area
                  yAxisId="right"
                  type="monotone"
                  dataKey="price"
                  name="Price (€/kWh)"
                  fill="#f59e0b"
                  stroke="#f59e0b"
                  fillOpacity={0.3}
                />
              </ComposedChart>
            )}
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};

export default ConsumptionChart;