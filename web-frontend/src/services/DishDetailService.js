import axios from "axios";
import { getApiUrl } from '../utils/utils';

export const getDishNames = () => axios.get(getApiUrl('/dishes/dishNames'));

export const getAllDishes = () => axios.get(getApiUrl('/dishes'));

export const getDishWithAllergiesById = (id) => axios.get(getApiUrl(`/dishes/${id}/details-with-allergies`));

export const getSafeDishes = (allergies) => {
    return axios.post(getApiUrl('/dishes/safeDishes'), allergies);
  };