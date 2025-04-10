interface YearSelectorProps {
    currentYear: number;
    onChange: (year: number) => void;
    minYear?: number;
    maxYear?: number;
  }
  
  const YearSelector = ({
    currentYear,
    onChange,
    minYear = 2020,
    maxYear = new Date().getFullYear(),
  }: YearSelectorProps) => {

    const years = Array.from(
      { length: maxYear - minYear + 1 },
      (_, i) => maxYear - i
    );
  
    return (
      <div className="flex items-center space-x-2">
        <label htmlFor="year-select" className="text-sm font-medium text-gray-700">
          Year:
        </label>
        <select
          id="year-select"
          value={currentYear}
          onChange={(e) => onChange(Number(e.target.value))}
          className="rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm"
        >
          {years.map((year) => (
            <option key={year} value={year}>
              {year}
            </option>
          ))}
        </select>
      </div>
    );
  };
  
  export default YearSelector;