import axios from 'axios';
import { getApiUrl } from '../utils/utils';

export const fetchDefaultShops = async (location) => {
  const response = await axios.post(getApiUrl('/feed/default'), location);
  return response.data;
};

export const filterShops = async (searchRequest) => {
  try {
    const token = localStorage.getItem('jwtToken'); 
    const response = await axios.post(getApiUrl('/feed/filter'), searchRequest, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });

    // Log the full response to inspect its structure
    console.log('API Response:', response);

    // Return the data (assuming response.data contains the shops array)
    return response.data;
  } catch (error) {
    console.error('Error during filter API call:', error.message);
    throw error;
  }
};