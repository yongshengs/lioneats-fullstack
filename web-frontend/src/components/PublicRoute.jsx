import React from 'react';
import { Navigate } from 'react-router-dom';

const PublicRoute = ({ children }) => {
    const isLoggedIn = localStorage.getItem('loggedIn') === 'true';

    return !isLoggedIn ? children : <Navigate to="/feed" />;
};

export default PublicRoute;
