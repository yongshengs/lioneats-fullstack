import React from 'react';
import { useNavigate } from 'react-router-dom';
import defaultLogo from '../images/logo_piconly.png'; 
import './styles/ShopCardComponent.css'; 

const GenericShopCardComponent = ({ shop }) => {
  const navigate = useNavigate();

  if (!shop) {
    return null; 
  }

  const {
    name,
    formatted_address: formattedAddress,
    user_ratings_total: userRatingsTotal,
    rating,
    photos,
    keyWord,
    place_id: placeId,
  } = shop;

  // Use the first photo's photo_reference, if not use default logo
  const imgSrc = photos && photos.length > 0 ? photos[0].photo_reference : defaultLogo;

  // Handle card click to navigate to Login
  const handleCardClick = () => {
    navigate('/login');
  };

  return (
    <div className="card mb-3" onClick={handleCardClick} style={{ cursor: 'pointer' }}>
      <div className="row no-gutters">
        <div className="col-md-4">
          <img alt="Shop Image" className="card-img" src={imgSrc} />
        </div>
        <div className="col-md-8">
          <div className="card-body">
            <h5 className="card-title">{name}</h5>
            <p className="card-text">Location: {formattedAddress}</p>
            <p className="card-text">Number of Ratings: {userRatingsTotal} Reviews</p>
            <p className="card-text">Rating: {rating} / 5</p>
            {keyWord && (
              <div className="mt-2">
                <span className="badge badge-primary custom-badge">{keyWord}</span>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default GenericShopCardComponent;
