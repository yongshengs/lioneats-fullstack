import React from 'react';
import { useNavigate } from 'react-router-dom';
import defaultLogo from '../images/logo_piconly.png'; 
import './styles/ShopCardComponent.css'; 

const ShopCardComponent = ({ shop }) => {
  const navigate = useNavigate();

  // Check if shop is undefined or null
  if (!shop) {
    return null; 
  }

  const {
    name,                       // Shop name
    formatted_address: formattedAddress,  // Address of the shop
    user_ratings_total: userRatingsTotal, // Total number of ratings
    rating,                     // Shop rating
    photos,                     // Array of photos
    keyWord,                    // Keyword or category for the shop
    website,                    // Shop website
    url,                        // Google Maps URL
    place_id: placeId,          // Unique place ID
    formatted_phone_number: formattedPhoneNumber, // Shop phone number
    price_level: priceLevel,    // Price level of the shop
    opening_hours: openingHours, // Opening hours (usually contains weekday_text array)
    reviews,                    // Array of reviews
    geometry,                   // Contains location details like lat and lng
  } = shop;

  // Use the first photo's photo_reference if available, otherwise use the default logo
  const imgSrc = photos && photos.length > 0 ? photos[0].photo_reference : defaultLogo;

  // Determine the URL for the shop's name link
  const shopUrl = website || url || '#';

  // Handle card click to navigate to ShopDetailPageComponent
  const handleCardClick = () => {
    navigate(`/shop/${placeId}`, { state: { shop } }); // Pass the shop object via state
  };

  // Determine price level display
  const renderPriceLevel = (priceLevel) => {
    if (priceLevel === 0) {
      return 'Price level: Unknown';
    }
    return `Price level: ${'$'.repeat(priceLevel)}`;
  };

  return (
    <div className="card mb-3" onClick={handleCardClick} style={{ cursor: 'pointer' }}>
      <div className="row no-gutters">
        <div className="col-md-4">
          <img alt="Shop Image" className="card-img" src={imgSrc} />
        </div>
        <div className="col-md-8">
          <div className="card-body">
            <h5 className="card-title">
              <a href={shopUrl} target="_blank" rel="noopener noreferrer" onClick={(e) => e.stopPropagation()}>
                {name}
              </a>
            </h5>
            <p className="card-text"> {formattedAddress}</p>
            <p className="card-text"> {userRatingsTotal} reviews </p>
            <p className="card-text"> {rating} / 5</p>
            <p className="card-text"> {renderPriceLevel(priceLevel)}</p> {/* Display the price level */}
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

export default ShopCardComponent;
