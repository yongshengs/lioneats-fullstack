import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import GenericShopCardComponent from './GenericShopCardComponent';
import GenericFilterComponent from './GenericFilterComponent';
import { fetchDefaultShops } from '../services/FeedService';

const GenericFeedComponent = () => {
  const navigate = useNavigate();
  const [shops, setShops] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchShops = async () => {
      try {
        const shopsData = await fetchDefaultShops();
        setShops(shopsData);
      } catch (error) {
        console.error('Error fetching shops:', error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchShops();
  }, []);

  const handleFilterChange = () => {
    // console.log('Filter changed - navigating to login');
    navigate('/login');
  };
  
  const handleShopClick = (e) => {
    e.preventDefault();
    // console.log('Shop clicked - navigating to login');
    navigate('/login');
  };

  return (
    <div>
      <div className="container mt-5">
        <div className="row">
          <div className="col-md-8">
            <h2>Places to Eat</h2>
            {loading ? (
              <div>Loading...</div>
            ) : shops.length === 0 ? (
              <div>No shops found.</div>
            ) : (
              shops.map((shop, index) => (
                <div key={index} onClick={handleShopClick}>
                  <GenericShopCardComponent shop={shop} />
                </div>
              ))
            )}
          </div>
          <div className="col-md-4">
            <GenericFilterComponent onFilterChange={handleFilterChange} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default GenericFeedComponent;
