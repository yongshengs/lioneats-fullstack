import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllDishes } from '../services/DishDetailService';
import './styles/DishListComponent.css';

const DishListComponent = () => {
  const [dishes, setDishes] = useState([]);

  useEffect(() => {
    getAllDishes()
      .then(response => setDishes(response.data))
      .catch(error => console.error('Error fetching dishes:', error));
  }, []);

  return (
    <div className="dish-list-container">
      {dishes.map(dish => (
        <div key={dish.id} className="dish-card">
          <Link to={`/dishes/${dish.id}`}>
            <img src={dish.imageUrl} alt={dish.dishDetailName} className="dish-image" />
            <div className="dish-name">
              {dish.dishDetailName}
            </div>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default DishListComponent;
