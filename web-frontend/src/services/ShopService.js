import axios from 'axios';
import { getApiUrl } from '../utils/utils';

export const getShopById = async (id) => {
  try {
    const response = await axios.get(getApiUrl(`/shop/${id}`));
    return response.data;
  } catch (error) {
    throw error;
  }
};
