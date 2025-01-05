import logo from './logo.svg';
import React from 'react';
import { Route, Routes, BrowserRouter} from 'react-router-dom';
import Navbar from './component/common/Navbar';
import Footer from './component/common/Footer';
import Home from './component/home/Home';
import AllRoomsPage from './component/booking_rooms/AllRoomsPage';
import './App.css';


function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Navbar/>
          <div className='content'>
            <Routes>
              <Route exact path='/home' element={<Home />}/>
              <Route path="/rooms" element={<AllRoomsPage />} />
            </Routes>
          </div>
        <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;
