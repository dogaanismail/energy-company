import React, { useEffect, useState } from 'react';
import { getConsumptions } from '../service/consumption.service';
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const ConsumptionComponent = () => {
  const [consumptions, setConsumptions] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchConsumptions = async () => {
      try {
        const data = await getConsumptions(
          'edb24e51-1c16-4df9-8ba5-845435cf66d7', 
          'e2d51caa-fb3a-4c80-a7e3-7350421aea10',
          2024 
        );
        setConsumptions(data);
      } catch (err) {
        setError(err.message || 'Failed to fetch consumptions');
      }
    };

    fetchConsumptions();
  }, []);

  // Process data for the chart
  const monthlyData = Array(12).fill(0); // Initialize an array for 12 months
  consumptions.forEach((consumption) => {
    const month = new Date(consumption.consumptionTime).getMonth(); // Extract month (0-11)
    monthlyData[month] += parseFloat(consumption.amount); // Sum up consumption amounts
  });

  const chartData = {
    labels: [
      'January', 'February', 'March', 'April', 'May', 'June',
      'July', 'August', 'September', 'October', 'November', 'December',
    ],
    datasets: [
      {
        label: 'Monthly Consumption (kWh)',
        data: monthlyData,
        backgroundColor: 'rgba(75, 192, 192, 0.6)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1,
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Monthly Energy Consumption (kWh)',
      },
    },
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Customer Consumptions</h1>
      {error && <p className="text-red-500">{error}</p>}
      <div className="bg-white p-4 rounded shadow">
        <Bar data={chartData} options={chartOptions} />
      </div>
    </div>
  );
};

export default ConsumptionComponent;