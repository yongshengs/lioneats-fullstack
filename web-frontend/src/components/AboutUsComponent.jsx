import React from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import logo from '../images/logo_full.png'; 
import './styles/AboutUsComponent.css';

const AboutUsComponent = () => {
  const navigate = useNavigate();

  const handleCTAClick = () => {
    const isLoggedIn = localStorage.getItem('loggedIn') === 'true';
    if (isLoggedIn) {
      navigate('/feed');
    } else {
      navigate('/');
    }
  };

  return (
    <div className="container">
      <div className="logo-container">
        <img src={logo} alt="Lioneats Logo" className="logo" />
      </div>
      <h1 className="header">About Lioneats</h1>
      <p className="body-text">
        Lioneats is an initiative started in 2024 by a group of post-grad students from the National University of 
        Singapore Institute of Systems Science (NUS-ISS) as part of their capstone project for their program. It aims to help 
        tourists, travellers or anyone new to Singapore's food culture and local cuisine and are curious to learn more 
        about it and get recommendations on where to find local dishes.
      </p>
      <p className="body-text">
        The application can be accessed via web or Android-based mobile devices. It has a built-in image recognition model that can 
        identify local dishes using images and then recommend a list of places that is likely to serve that particular dish.
      </p>
      <div className="cta-container">
        <button className="btn btn-primary" onClick={handleCTAClick}>
          Go back to feed
        </button>
      </div>
    </div>
  );
};

export default AboutUsComponent;
