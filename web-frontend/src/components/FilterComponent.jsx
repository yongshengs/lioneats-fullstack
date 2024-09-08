import React, { useState, useEffect } from 'react';
import { getAllergyNames } from '../services/AllergyService';
import { getDishNames } from '../services/DishDetailService';
import { getMRTStations } from '../services/MRTService';
import './styles/FilterComponent.css';

const FilterComponent = ({ onFilterChange, filters }) => {
  const [allergiesList, setAllergiesList] = useState([]);
  const [dishPreferencesList, setDishPreferencesList] = useState([]);
  const [mrtStationsList, setMRTStationsList] = useState([]);

  useEffect(() => {
    getAllergyNames()
      .then(response => {
        const sortedAllergies = response.data.sort();
        setAllergiesList(sortedAllergies);
      })
      .catch(console.error);

    getDishNames()
      .then(response => {
        const sortedDishes = response.data.sort();
        setDishPreferencesList(sortedDishes);
      })
      .catch(console.error);

    getMRTStations()
      .then(response => {
        const sortedMRTStations = response.sort((a, b) => a.name.localeCompare(b.name));
        setMRTStationsList(sortedMRTStations);
      })
      .catch(console.error);
  }, []);

  useEffect(() => {
    if (filters) {
      onFilterChange(filters); // Apply initial filters if they exist
    }
  }, [filters, onFilterChange]);

  const handleFilterChange = (e) => {
    const { name, value, type, checked } = e.target;

    const updatedFilters = {
      ...filters,
      [name]: type === 'checkbox'
        ? (checked ? [...(filters[name] || []), value] : (filters[name] || []).filter(v => v !== value))
        : name === 'rating'
        ? parseFloat(value.replace('>', '').trim())
        : value,
    };

    onFilterChange(updatedFilters);
  };

  return (
    <div className="filter-container">
      <h3 className="filter-header">Search Filters</h3>

      <div className="filter-section">
        <h5 className="filter-subheader">Allergies</h5>
        {allergiesList.map(allergy => (
          <div key={allergy} className="filter-item">
            <input
              type="checkbox"
              name="allergies"
              value={allergy}
              onChange={handleFilterChange}
              checked={filters?.allergies?.includes(allergy)} // Use optional chaining to avoid errors
            />
            <label className="filter-label">{allergy}</label>
          </div>
        ))}
      </div>

      <div className="filter-section">
        <h5 className="filter-subheader">Dish Preferences</h5>
        {dishPreferencesList.map(dish => (
          <div key={dish} className="filter-item">
            <input
              type="checkbox"
              name="dishes"
              value={dish}
              onChange={handleFilterChange}
              checked={filters?.dishes?.includes(dish)} 
            />
            <label className="filter-label">{dish}</label>
          </div>
        ))}
      </div>
      
      <div className="filter-section">
        <h5 className="filter-subheader">Budget</h5>
        {['HIGH', 'MEDIUM', 'LOW'].map(budget => (
          <div key={budget} className="filter-item">
            <input
              type="radio"
              name="budget"
              value={budget}
              onChange={handleFilterChange}
              checked={filters?.budget === budget} 
            />
            <label className="filter-label">{budget}</label>
          </div>
        ))}
      </div>
      
      <div className="filter-section">
        <h5 className="filter-subheader">MRT Stations</h5>
        {Array.isArray(mrtStationsList) && mrtStationsList.length > 0 ? (
          mrtStationsList.map(station => (
            <div key={station.id} className="filter-item">
              <input
                type="checkbox"
                name="location"
                value={station.name}
                onChange={handleFilterChange}
                checked={filters?.location?.includes(station.name)} 
              />
              <label className="filter-label">{station.name}</label>
            </div>
          ))
        ) : (
          <div>Loading MRT stations...</div>
        )}
      </div>
      
      <div className="filter-section">
        <h5 className="filter-subheader">Rating (out of 5)</h5>
        {['> 4.5', '> 4', '> 3.5', '> 3', '> 2.5', '> 2', '> 1'].map(rating => (
          <div key={rating} className="filter-item">
            <input
              type="radio"
              name="rating"
              value={rating}
              onChange={handleFilterChange}
              checked={filters?.rating === parseFloat(rating.replace('>', '').trim())}
            />
            <label className="filter-label">{rating}</label>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FilterComponent;
