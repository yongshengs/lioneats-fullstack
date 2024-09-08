import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import LoginComponent from './components/LoginComponent';
import RegisterAccountComponent from './components/RegisterAccountComponent';
import CustomFeedComponent from './components/CustomFeedComponent';
import GenericFeedComponent from './components/GenericFeedComponent';
import ViewProfileComponent from './components/ViewProfileComponent';
import EditProfileComponent from './components/EditProfileComponent';
import ChangePasswordComponent from './components/ChangePasswordComponent'; 
import UploadImageComponent from './components/UploadImageComponent';
import UploadImageSuccessComponent from './components/UploadImageSuccessComponent';
import AboutUsComponent from './components/AboutUsComponent';
import DishListComponent from './components/DishListComponent';
import DishDetailPageComponent from './components/DishDetailPageComponent';
import ShopDetailPageComponent from './components/ShopDetailPageComponent';
import PrivateRoute from './components/PrivateRoute'; 
import PublicRoute from './components/PublicRoute';
import ErrorBoundary from './components/ErrorBoundary'; // Import the ErrorBoundary
import './App.css'

function App() {

  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <ErrorBoundary>
          <Routes>
            <Route path="/" element={
              <PublicRoute>
                <GenericFeedComponent />
              </PublicRoute>
            } />
            <Route path="/login" element={<LoginComponent />} />
            <Route path="/registerAccount" element={<RegisterAccountComponent />} />
            <Route path="/feed" element={
              <PrivateRoute>
                <CustomFeedComponent />
              </PrivateRoute>
            } />
            <Route path="/profile" element={
              <PrivateRoute>
                <ViewProfileComponent />
              </PrivateRoute>
            } />
            <Route path="/profile/edit" element={
              <PrivateRoute>
                <EditProfileComponent />
              </PrivateRoute>
            } />
            <Route path="/profile/change-password" element={
              <PrivateRoute>
                <ChangePasswordComponent />
              </PrivateRoute>
            } /> 
            <Route path="/upload" element={
              <PrivateRoute>
                <UploadImageComponent />
              </PrivateRoute>
            } />
            <Route path="/upload_success" element={
              <PrivateRoute>
                <UploadImageSuccessComponent />
              </PrivateRoute>
            } />
            <Route path="/about" element={<AboutUsComponent />} />
            <Route path="/dishes" element={<DishListComponent />} />
            <Route path="/dishes/:id" element={<DishDetailPageComponent />} />
            <Route path="/shop/:placeId" element={
              <PrivateRoute>
                <ShopDetailPageComponent />
              </PrivateRoute>
            } />
          </Routes>
        </ErrorBoundary>
      </BrowserRouter>
    </>
  )
}

export default App;
