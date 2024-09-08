import React from 'react';
import { useLocation } from 'react-router-dom';
import defaultLogo from '../images/logo_piconly.png'; 
import './styles/ShopDetailPageComponent.css';

const ShopDetailPageComponent = () => {
  const location = useLocation();
  const { shop } = location.state || {};

  if (!shop) {
    return <div>No shop details available.</div>;
  }

  const {
    name,
    formatted_address: formattedAddress,
    formatted_phone_number: formattedPhoneNumber,
    rating,
    user_ratings_total: userRatingsTotal,
    website,
    url,
    photos,
    reviews,
    opening_hours: openingHours,
    keyWord,
    price_level: priceLevel,
    geometry,
  } = shop;

  const imgSrc = photos && photos.length > 0 ? photos[0].photo_reference : defaultLogo;
  const mapUrl = `https://www.google.com/maps?q=${geometry.location.lat},${geometry.location.lng}`;

  // Function to render the price level
  const renderPriceLevel = (priceLevel) => {
    if (priceLevel === 0) {
      return 'Price level: Unknown';
    }
    return `Price level: ${'$'.repeat(priceLevel)}`;
  };

  return (
    <div className="container mt-5">
      {/* Photo Gallery Section */}
      <div className="photo-gallery">
        {photos && photos.slice(0, 5).map((photo, index) => (
          <img
            key={index}
            src={photo.photo_reference}
            alt={`${name} Image ${index + 1}`}
            className="gallery-img"
          />
        ))}
      </div>

      <h2>{name}</h2> 

      <div className="shop-info-box">
        <div className="address">{formattedAddress}</div>
        <div className="ratings"> 
          <strong>{rating} out of 5</strong> stars based on <strong>{userRatingsTotal} reviews</strong> from users
        </div>
        <div className="price-level">
          {renderPriceLevel(priceLevel)} 
        </div>
        <div><a href={website || url} target="_blank" rel="noopener noreferrer">Visit shop website here</a></div>
        <div className="known-for"><strong>Known for:</strong> <div className="badge badge-primary custom-badge">{keyWord}</div></div>
      </div>

      <div className="shop-details">
        <div>
          <strong>Opening Hours:</strong>
          {openingHours && openingHours.weekday_text && openingHours.weekday_text.length > 0 && (
            <ul>
              {openingHours.weekday_text.map((hour, index) => (
                <li key={index}>{hour}</li>
              ))}
            </ul>
          )}
        </div>
      </div>

      <div className="reviews-section">
        <h3>Reviews</h3>
        {reviews && reviews.length > 0 ? (
          reviews.map((review, index) => (
            <div key={index} className="review">
              <strong>{review.author_name}</strong>: {review.rating} / 5
              <p>{review.text}</p>
            </div>
          ))
        ) : (
          <p>No reviews available.</p>
        )}
      </div>
    </div>
  );
};

export default ShopDetailPageComponent;
