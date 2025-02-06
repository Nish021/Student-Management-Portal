
import { Route, BrowserRouter, Routes } from 'react-router-dom';
import './App.css';
import AddStudent from './Components/AddStudent/AddStudent';
import Dashboard from './Components/DashBoard/Dashboard';
import Navbar from './Components/Navbar/Navbar';
import EditStudent from './Components/EditStudent/EditStudent';

function App() {
  return (
    <>
      <Navbar/>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Dashboard/>}/>
          <Route path="/add" element={<AddStudent/>}/>
          <Route path="/edit/:id" element={<EditStudent/>}/>
        </Routes>     
      </BrowserRouter>
    </>
  );
}

export default App;
