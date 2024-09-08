import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserService';
import { getAllDishes } from '../services/DishDetailService';

const ViewProfileComponent = ({ onEdit }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [dishDetails, setDishDetails] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const userId = localStorage.getItem('userId'); 
        if (!userId) {
            console.error('No user ID found in localStorage.');
            setLoading(false);
            return;
        }

        const fetchUserProfile = async () => {
            try {
                const userProfile = await getUserProfile(userId);
                setUser(userProfile.data);

                // Fetch all dish details
                const dishResponse = await getAllDishes();
                setDishDetails(dishResponse.data);

            } catch (error) {
                console.error('Error fetching user profile:', error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUserProfile();
    }, []);

    const handleEditProfile = () => {
        navigate('/profile/edit'); 
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!user) {
        return <div>User not found.</div>;
    }

    // Get dish details for the user's preferences
    const userDishPreferences = dishDetails.filter(dish => user.dishPreferences.includes(dish.dishDetailName));

    return (
        <div className="container mt-5">
            <h2>Profile</h2>
            <div className="profile-details">
                <p><strong>Name:</strong> {user.name}</p>
                <p><strong>Username:</strong> {user.username}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Age:</strong> {user.age}</p>
                <p><strong>Gender:</strong> {user.gender}</p>
                <p><strong>Country:</strong> {user.country}</p>
                <p><strong>Preferred Budget:</strong> {user.preferredBudget}</p>
                <p><strong>Likes Spicy:</strong> {user.likesSpicy ? 'Yes' : 'No'}</p>
                <p><strong>Allergies:</strong> {user.allergies.join(', ')}</p>
                <p><strong>Dish Preferences:</strong></p>
                <div className="dish-preferences-container">
                    {userDishPreferences.map((dish, index) => (
                        <div
                            key={index}
                            className="dish-card"
                        >
                            <img src={dish.imageUrl} alt={dish.dishDetailName} className="dish-card-image" />
                            <div className="dish-card-content">
                                <div className="dish-card-title">{dish.dishDetailName}</div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <button className="btn btn-primary" onClick={handleEditProfile}>Edit Profile</button>
            <br />
            <br />
            <button className="btn btn-secondary ml-2" onClick={() => navigate('/profile/change-password')}>Change Password</button>
        </div>
    );
};

export default ViewProfileComponent;
