import logo from './logo.svg';
import React from 'react';
import { Route, Routes, BrowserRouter} from 'react-router-dom';
import Navbar from './component/common/Navbar';
import Footer from './component/common/Footer';
import Home from './component/home/Home';
import AllRoomsPage from './component/booking_rooms/AllRoomsPage';
import FindBookingPage from './component/booking_rooms/FindMyBookingPage';
import RoomDetailsPage from './component/booking_rooms/RoomDetailsPage';
import LoginPage from './component/auth/LoginPage';
import RegisterPage from './component/auth/RegisterPage';
import ProfilePage from './component/profile/ProfilePage';
import EditProfilePage from './component/profile/EditProfile';
import {ProtectedRoute, AdminRoute} from './service/guard';
import './App.css';


function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <Navbar/>
          <div className='content'>
            <Routes>

              {/* Public Routes */}
              <Route exact path='/home' element={<Home />}/>
              <Route exact path="/rooms" element={<AllRoomsPage />} />
              <Route exact path="/find-bookings" element={<FindBookingPage />} />
              <Route exact path="/login" element={<LoginPage />} />
              <Route exact path="/register" element={<RegisterPage />} />
              
              
              {/* Authenticated user routes */}
              <Route exact path="/room-details-book/:roomId" element={ <ProtectedRoute element={<RoomDetailsPage />} />}/>
              <Route exact path="/profile" element={ <ProtectedRoute element={<ProfilePage />} />} />
              <Route exact path="/edit-profile" element={ <ProtectedRoute element={<EditProfilePage />} />} />
            </Routes>
          </div>
        <Footer/>
      </div>
    </BrowserRouter>
  );
}

export default App;
