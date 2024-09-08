import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createUser } from '../services/UserService';
import { getAllergyNames } from '../services/AllergyService';
import { getAllDishes, getSafeDishes } from '../services/DishDetailService'; 
import './styles/RegisterAccountComponent.css';

const RegisterAccountComponent = () => {
  const [user, setUser] = useState({
    name: '',
    username: '',
    password: '',
    email: '',
    age: '',
    gender: '',
    country: '',
    preferredBudget: '',
    likesSpicy: false,
    allergies: [],
    dishPreferences: [],
  });

  const [allergiesList, setAllergiesList] = useState([]);
  const [dishPreferencesList, setDishPreferencesList] = useState([]);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  
  const navigate = useNavigate();

  useEffect(() => {
    getAllergyNames()
      .then(response => setAllergiesList(response.data))
      .catch(error => console.error('Error fetching allergies:', error));

    // Initially fetch all dishes if no allergies are selected
    getAllDishes()
      .then(response => setDishPreferencesList(response.data))
      .catch(error => console.error('Error fetching dish preferences:', error));
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setUser(prevUser => ({
      ...prevUser,
      [name]: type === 'checkbox' ? checked : value,
    }));
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

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    createUser(user)
      .then(response => {
        localStorage.setItem('registrationSuccess', 'Your account has been created successfully! Please log in.');
        localStorage.removeItem('loggedIn'); 
        localStorage.removeItem('userId'); 
        navigate('/login'); 
      })
      .catch(error => {
        if (error.response) {
          setError(error.response.data);
        } else {
          setError('An unexpected error occurred.');
        }
      });
  };

  return (
    <div className="container mt-5">
      <h1>Register Account</h1>
      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={user.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            className="form-control"
            id="username"
            name="username"
            value={user.username}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            className="form-control"
            id="password"
            name="password"
            value={user.password}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            value={user.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="age">Age:</label>
          <input
            type="number"
            className="form-control"
            id="age"
            name="age"
            value={user.age}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="gender">Gender:</label>
          <select
            className="form-control"
            id="gender"
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
          <label htmlFor="country">Country:</label>
          <input
            type="text"
            className="form-control"
            id="country"
            name="country"
            value={user.country}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="preferredBudget">Preferred Budget:</label>
          <select
            className="form-control"
            id="preferredBudget"
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

        <br />

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

        <br />

        <div className="form-group">
          <label>Allergies:</label>
          {allergiesList.map(allergy => (
            <div key={allergy} className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                id={allergy}
                name="allergies"
                value={allergy}
                checked={user.allergies.includes(allergy)}
                onChange={handleAllergyChange}
              />
              <label className="form-check-label" htmlFor={allergy}>
                {allergy}
              </label>
            </div>
          ))}
        </div>

        <br />

        <div className="form-group">
          <label>Dish Preferences:</label>
          <div className="dish-preferences-container">
            {dishPreferencesList.map((dish, index) => (
              <div
                key={index} // Using index as the unique key
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

        <button className="btn btn-primary" type="submit">
          Register Account
        </button>
      </form>
    </div>
  );
};

export default RegisterAccountComponent;
