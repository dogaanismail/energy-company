import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ConsumptionComponent from './components/consumption.component';
import LoginComponent from './components/login.component';
import RegisterComponent from './components/register.component';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/consumptions" element={<ConsumptionComponent />} />
        <Route path="/login" element={<LoginComponent />} />
        <Route path="/register" element={<RegisterComponent />} />
      </Routes>
    </Router>
  );
};


export default App
