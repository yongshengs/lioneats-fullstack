import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserProfile, updateUserProfile } from '../services/UserService';
import { getAllergyNames } from '../services/AllergyService';
import { getAllDishes, getSafeDishes } from '../services/DishDetailService';
// import './styles/EditProfileComponent.css'; // Import the CSS file for styling

const EditProfileComponent = () => {
    const [user, setUser] = useState({
        name: '',
        username: '',
        email: '',
        age: '',
        gender: '',
        country: '',
        preferredBudget: '',
        likesSpicy: false,
        allergies: [],
        dishPreferences: [],
    });

    const [password, setPassword] = useState('');
    const [allergiesList, setAllergiesList] = useState([]);
    const [dishPreferencesList, setDishPreferencesList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
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

                // Fetch allergies
                const allergiesResponse = await getAllergyNames();
                setAllergiesList(allergiesResponse.data);

                // Fetch dishes based on the user's current allergies
                const safeDishesResponse = userProfile.data.allergies.length
                    ? await getSafeDishes(userProfile.data.allergies)
                    : await getAllDishes();

                setDishPreferencesList(safeDishesResponse.data);

            } catch (error) {
                console.error('Error fetching user profile:', error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUserProfile();
    }, []);

    const handleChange = (event) => {
        const { name, value, type, checked } = event.target;
        setUser(prevUser => ({
            ...prevUser,
            [name]: type === 'checkbox' ? checked : value,
        }));
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleAllergyChange = async (e) => {
        const { value, checked } = e.target;
        const updatedAllergies = checked
            ? [...user.allergies, value]
            : user.allergies.filter(allergy => allergy !== value);

        setUser(prevUser => ({
            ...prevUser,
            allergies: updatedAllergies,
            dishPreferences: [], // Clear dish preferences when allergies change
        }));

        if (updatedAllergies.length > 0) {
            try {
                const safeDishesResponse = await getSafeDishes(updatedAllergies);
                setDishPreferencesList(safeDishesResponse.data);
            } catch (error) {
                console.error('Error fetching safe dishes:', error);
            }
        } else {
            // If no allergies are selected, show all dishes
            try {
                const allDishesResponse = await getAllDishes();
                setDishPreferencesList(allDishesResponse.data);
            } catch (error) {
                console.error('Error fetching all dishes:', error);
            }
        }
    };

    const handleDishPreferenceChange = (dishName) => {
        setUser(prevUser => {
            const newDishPreferences = prevUser.dishPreferences.includes(dishName)
                ? prevUser.dishPreferences.filter(d => d !== dishName)
                : [...prevUser.dishPreferences, dishName];
            return { ...prevUser, dishPreferences: newDishPreferences };
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError(null);

        if (!password) {
            setError('Please enter your password to confirm changes.');
            return;
        }

        try {
            const userId = localStorage.getItem('userId');
            await updateUserProfile(userId, { ...user, password });

            localStorage.setItem('updateSuccess', 'Your profile has been updated successfully! Please log in again.');
            localStorage.removeItem('loggedIn');
            localStorage.removeItem('userId');
            navigate('/login');
        } catch (error) {
            console.error('Error updating user profile:', error.message);
            setError('Error updating profile. Please try again.');
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mt-5">
            <h2>Edit Profile</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Name</label>
                    <input
                        type="text"
                        className="form-control"
                        name="name"
                        value={user.name}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Username</label>
                    <input
                        type="text"
                        className="form-control"
                        name="username"
                        value={user.username}
                        readOnly
                    />
                </div>

                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="email"
                        className="form-control"
                        name="email"
                        value={user.email}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Age</label>
                    <input
                        type="number"
                        className="form-control"
                        name="age"
                        value={user.age}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Gender</label>
                    <select
                        className="form-control"
                        name="gender"
                        value={user.gender}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>

                <div className="form-group">
                    <label>Country</label>
                    <input
                        type="text"
                        className="form-control"
                        name="country"
                        value={user.country}
                        onChange={handleChange}
                    />
                </div>

                <div className="form-group">
                    <label>Preferred Budget</label>
                    <select
                        className="form-control"
                        name="preferredBudget"
                        value={user.preferredBudget}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select Preferred Budget</option>
                        <option value="HIGH">High</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="LOW">Low</option>
                    </select>
                </div>

                <br/>

                <div className="form-group form-check">
                    <input
                        type="checkbox"
                        className="form-check-input"
                        id="likesSpicy"
                        name="likesSpicy"
                        checked={user.likesSpicy}
                        onChange={handleChange}
                    />
                    <label className="form-check-label" htmlFor="likesSpicy">
                        Likes Spicy
                    </label>
                </div>

                <br/>

                <div className="form-group">
                    <label>Allergies:</label>
                    {allergiesList.map((allergy, index) => (
                        <div key={index} className="form-check">
                            <input
                                type="checkbox"
                                className="form-check-input"
                                id={`allergy-${index}`}
                                name="allergies"
                                value={allergy}
                                checked={user.allergies.includes(allergy)}
                                onChange={handleAllergyChange}
                            />
                            <label className="form-check-label" htmlFor={`allergy-${index}`}>
                                {allergy}
                            </label>
                        </div>
                    ))}
                </div>

                <br/>

                <div className="form-group">
                    <label>Dish Preferences:</label>
                    <div className="dish-preferences-container">
                        {dishPreferencesList.map((dish, index) => (
                            <div
                                key={index}
                                className={`dish-card ${user.dishPreferences.includes(dish.dishDetailName) ? 'selected' : ''}`}
                                onClick={() => handleDishPreferenceChange(dish.dishDetailName)}
                            >
                                <img src={dish.imageUrl} alt={dish.dishDetailName} className="dish-card-image" />
                                <div className="dish-card-content">
                                    <div className="dish-card-title">{dish.dishDetailName}</div>
                                    <input
                                        type="checkbox"
                                        className="dish-checkbox"
                                        checked={user.dishPreferences.includes(dish.dishDetailName)}
                                        readOnly
                                    />
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                <div className="form-group">
                    <label>Password (required to confirm changes)</label>
                    <input
                        type="password"
                        className="form-control"
                        name="password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                </div>

                <button type="submit" className="btn btn-primary">Save Changes</button>
            </form>
        </div>
    );
};

export default EditProfileComponent;
