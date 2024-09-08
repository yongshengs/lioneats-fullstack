import React from 'react';
import { Link } from 'react-router-dom';
import './styles/ErrorPage.css';

const ErrorPage = ({ message }) => {
    return (
        <div className="error-container">
            <h1>Oops!</h1>
            <p>{message || "Sorry, we are facing some issues with our backend! Please continue to enjoy our app."}</p>
            <Link to="/" className="btn btn-primary">
                Go Back to Home
            </Link>
        </div>
    );
};

export default ErrorPage;
