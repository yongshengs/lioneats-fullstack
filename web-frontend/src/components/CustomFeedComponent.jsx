import React, { useState, useEffect } from 'react';
import ShopCardComponent from './ShopCardComponent';
import FilterComponent from './FilterComponent'; 
import { fetchDefaultShops, filterShops } from '../services/FeedService';
import { getUserProfile } from '../services/UserService'; 
import { getNearestMRTs } from '../services/MRTService'; 

const CustomFeedComponent = () => {
  const [shops, setShops] = useState([]);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);
  const [nearestMRT, setNearestMRT] = useState('');
  const [filters, setFilters] = useState({
    allergies: [],
    dishes: [],
    budget: '',
    location: [],
    rating: '',
  });

  // Function to get user location
  const getUserLocation = () => {
    return new Promise((resolve, reject) => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            resolve({ latitude, longitude });
          },
          (error) => {
            console.error('Error getting user location:', error.message);
            resolve(null); 
          }
        );
      } else {
        console.error('Geolocation is not supported by your browser');
        resolve(null); 
      }
    });
  };

  // Function to fetch shops
  const fetchShops = async (latitude, longitude) => {
    try {
      const location = latitude && longitude ? { latitude, longitude } : null;
      const shopsData = await fetchDefaultShops(location);
      setShops(shopsData);
    } catch (error) {
      console.error('Error fetching shops:', error.message);
    } finally {
      setLoading(false);
    }
  };

  // Function to get the nearest MRT station
  const fetchNearestMRT = async (latitude, longitude) => {
    try {
      const userLocation = { latitude, longitude };
      const nearestMRTs = await getNearestMRTs(userLocation);
      if (nearestMRTs && nearestMRTs.length > 0) {
        const mrtName = nearestMRTs[0].name;
        setNearestMRT(mrtName);
        setFilters(prevFilters => ({
          ...prevFilters,
          location: [mrtName], 
        }));
        // console.log('Nearest MRT:', mrtName);
      }
    } catch (error) {
      console.error('Error fetching nearest MRT station:', error.message);
    }
  };

  
  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const userId = localStorage.getItem('userId'); 
        if (!userId) {
          console.error('No user ID found in localStorage.');
          setLoading(false);
          return;
        }
        const userProfile = await getUserProfile(userId);
        const userData = userProfile.data;

        // Ensure filters are initialized correctly with user data
        setUser(userData);
        setFilters({
          allergies: userData.allergies || [],
          dishes: userData.dishPreferences || [],
          budget: userData.preferredBudget || '',
          location: [], 
          rating: '',
        });
      } catch (error) {
        console.error('Error fetching user profile:', error.message);
      }
    };

    fetchUserProfile(); 
  }, []);

  
  useEffect(() => {
    const initFetchShops = async () => {
      const location = await getUserLocation(); 
      if (location) {
        const { latitude, longitude } = location;
        await fetchNearestMRT(latitude, longitude); 
        await fetchShops(latitude, longitude); 
      } else {
        await fetchShops(null, null); 
      }
    };

    initFetchShops();
  }, []);

  useEffect(() => {
    const applyFilters = async () => {
      setLoading(true);
      try {
        let filteredShops = await filterShops(filters);
  
        // Log the filteredShops to inspect the response
        console.log('Filtered shops:', filteredShops);
  
        // Ensure filteredShops is an array before applying .filter()
        if (Array.isArray(filteredShops)) {
          // Apply the rating filter on the frontend
          if (filters.rating) {
            filteredShops = filteredShops.filter(shop => shop.rating >= filters.rating);
          }
  
          // Apply the budget filter on the frontend
          if (filters.budget) {
            filteredShops = filteredShops.filter(shop => {
              if (filters.budget === 'HIGH') {
                return true; 
              } else if (filters.budget === 'MEDIUM') {
                return shop.price_level >= 0 && shop.price_level <= 4;
              } else if (filters.budget === 'LOW') {
                return shop.price_level >= 0 && shop.price_level <= 2;
              }
              return true;
            });
          }
          setShops(filteredShops);
        } else {
          console.error('Expected an array but received:', filteredShops);
        }
      } catch (error) {
        console.error('Error filtering shops:', error.message);
      } finally {
        setLoading(false);
      }
    };
  
    if (
      filters.allergies.length > 0 ||
      filters.dishes.length > 0 ||
      filters.budget ||
      filters.location.length > 0 ||
      filters.rating
    ) {
      applyFilters();
    } else {
      fetchDefaultShops().then(setShops).catch(console.error);
    }
  }, [filters]);
  

  const handleFilterChange = (newFilters) => {
    setFilters(newFilters);
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
                <ShopCardComponent
                  key={index}
                  shop={shop}
                />
              ))
            )}
          </div>
          <div className="col-md-4">
            <FilterComponent onFilterChange={handleFilterChange} filters={filters} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default CustomFeedComponent;
