import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getDishWithAllergiesById } from '../services/DishDetailService';
import './styles/DishDetailPageComponent.css';

const DishDetailPageComponent = () => {
  const { id } = useParams();
  const [dishDetail, setDishDetail] = useState(null);

  useEffect(() => {
    getDishWithAllergiesById(id)
      .then(response => {
        setDishDetail(response.data);
      })
      .catch(error => console.error('Error fetching dish detail:', error));
  }, [id]);

  if (!dishDetail) {
    return <div>Loading...</div>;
  }

  return (
    <div className="dish-detail-container">
      <img src={dishDetail.imageUrl} alt={dishDetail.name} className="dish-detail-image" />
      <h2>{dishDetail.name}</h2>
      <div className="dish-detail-section">
        <h3 className="section-header">Description</h3>
        <p className="section-text">{dishDetail.description}</p>
      </div>
      <div className="dish-detail-section">
        <h3 className="section-header">History</h3>
        <p className="section-text">{dishDetail.history}</p>
      </div>
      <div className="dish-detail-section">
        <h3 className="section-header">Ingredients</h3>
        <p className="section-text">{dishDetail.ingredients}</p>
      </div>
      <div className="dish-detail-section">
        <h3 className="section-header">Spicy</h3>
        <p className="section-text">{dishDetail.isSpicy ? 'Yes' : 'No'}</p>
      </div>
      <div className="dish-detail-section">
        <h3 className="section-header">Allergens</h3>
        <p className="section-text">{dishDetail.allergies && dishDetail.allergies.length > 0 ? dishDetail.allergies.map(a => a.name).join(', ') : 'None'}</p>
      </div>
      <div className="cta-container">
        <Link to="/dishes" className="btn btn-primary">Back to Dish List</Link>
      </div>
    </div>
  );
};

export default DishDetailPageComponent;
