import axios from 'axios';
import { getApiUrl } from '../utils/utils';

export const getMRTStations = async () => {
  try {
    const response = await axios.get(getApiUrl('/mrt/all'));
    return response.data;
  } catch (error) {
    console.error('Error fetching MRT stations:', error.message);
    throw error;
  }
};

export const getNearestMRTs = async (userLocation) => {
  try {
    const response = await axios.post(getApiUrl(`/mrt/nearest/1`), userLocation);  
    return response.data;
  } catch (error) {
    console.error('Error fetching nearest MRT stations:', error.message);
    throw error;
  }
};
