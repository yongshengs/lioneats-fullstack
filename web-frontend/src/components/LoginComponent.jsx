import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { loginUser } from '../services/AuthService';

const LoginComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const registrationSuccess = localStorage.getItem('registrationSuccess');
        const updateSuccess = localStorage.getItem('updateSuccess');
        const changePasswordSuccess = localStorage.getItem('changePasswordSuccess');

        if (registrationSuccess) {
            setSuccessMessage(registrationSuccess);
            localStorage.removeItem('registrationSuccess'); // Clear the success message after displaying it
        }

        if (updateSuccess) {
            setSuccessMessage(updateSuccess);
            localStorage.removeItem('updateSuccess'); // Clear the success message after displaying it
        }

        if (changePasswordSuccess) {
            setSuccessMessage(changePasswordSuccess);
            localStorage.removeItem('changePasswordSuccess'); // Clear the success message after displaying it
        }
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await loginUser({ username, password });

            // Extract JWT token and user data from response and store them in localStorage
            const { jwt, userId, username: loggedInUsername } = response.data;

            if (jwt) {
                localStorage.setItem('jwtToken', jwt);
                localStorage.setItem('loggedIn', 'true');
                localStorage.setItem('userId', userId);
                localStorage.setItem('username', loggedInUsername); // Save the username to local storage
                window.dispatchEvent(new Event('storage')); // Dispatch storage event to update HeaderComponent
            }

            navigate('/feed');
        } catch (error) {
            console.error('Login failed:', error.response?.data || error.message);
            setError('Invalid username or password');
        }
    };

    return (
        <div className="container mt-5">
            <h1>Login</h1>
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <input
                        className="form-control"
                        name="username"
                        placeholder="Username"
                        required
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <br />
                <div className="form-group">
                    <input
                        className="form-control"
                        name="password"
                        placeholder="Password"
                        required
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <br />
                {error && <div className="alert alert-danger">{error}</div>}
                <button className="btn btn-primary" type="submit">Login</button>
            </form>
            <div className="mt-3">
                <p>Don't have an account with us? <Link to="/registerAccount">Register a new account here!</Link></p>
            </div>
        </div>
    );
};

export default LoginComponent;
