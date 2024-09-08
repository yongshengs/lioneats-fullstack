import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { changePassword } from '../services/UserService';

const ChangePasswordComponent = () => {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmNewPassword, setConfirmNewPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleChangePassword = async (event) => {
        event.preventDefault();
        setError(null);

        const userId = localStorage.getItem('userId');
        const requestBody = {
            oldPassword,
            newPassword,
            confirmNewPassword,
        };

        try {
            await changePassword(userId, requestBody);
            // Clear local storage and redirect to login after a successful password change
            localStorage.removeItem('loggedIn');
            localStorage.removeItem('userId');
            localStorage.removeItem('jwtToken');
            localStorage.setItem('changePasswordSuccess', 'Your password has been changed successfully. Please log in again.');
            navigate('/login');
        } catch (errorResponse) {
            if (errorResponse.response && errorResponse.response.data) {
                setError(errorResponse.response.data);
            } else {
                setError({ general: 'Failed to change password. Please try again.' });
            }
            // Clear the input fields
            setOldPassword('');
            setNewPassword('');
            setConfirmNewPassword('');
        }
    };

    return (
        <div className="container mt-5">
            <h2>Change Password</h2>
            {error && (
                <div className="alert alert-danger">
                    {typeof error === 'string' ? (
                        <span>{error}</span>
                    ) : (
                        Object.values(error).map((errMsg, index) => (
                            <p key={index}>{errMsg}</p>
                        ))
                    )}
                </div>
            )}
            <form onSubmit={handleChangePassword}>
                <div className="form-group">
                    <label>Old Password</label>
                    <input
                        type="password"
                        className="form-control"
                        value={oldPassword}
                        onChange={(e) => setOldPassword(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>New Password</label>
                    <input
                        type="password"
                        className="form-control"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Confirm New Password</label>
                    <input
                        type="password"
                        className="form-control"
                        value={confirmNewPassword}
                        onChange={(e) => setConfirmNewPassword(e.target.value)}
                        required
                    />
                </div>

                <button type="submit" className="btn btn-primary">Change Password</button>
            </form>
        </div>
    );
};

export default ChangePasswordComponent;
