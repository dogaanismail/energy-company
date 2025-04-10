import '@testing-library/jest-dom';
import { render, screen, fireEvent } from '@testing-library/react';
import YearSelector from '../components/ui/YearSelector';

describe('YearSelector', () => {

  it('renders the YearSelector component with the correct initial year', () => {
    render(
      <YearSelector
        currentYear={2023}
        onChange={() => {}}
        minYear={2020}
        maxYear={2025}
      />
    );

    const selectElement = screen.getByLabelText(/year:/i);
    expect(selectElement).toBeInTheDocument();
    expect(selectElement).toHaveValue('2023');
  });
  
  it('calls onChange with the correct year when a new year is selected', () => {
    const onChangeMock = jest.fn();

    render(
      <YearSelector
        currentYear={2023}
        onChange={onChangeMock}
        minYear={2020}
        maxYear={2025}
      />
    );

    const selectElement = screen.getByLabelText(/year:/i);

    fireEvent.change(selectElement, { target: { value: '2022' } });

    expect(onChangeMock).toHaveBeenCalledTimes(1);
    expect(onChangeMock).toHaveBeenCalledWith(2022);
  });
});