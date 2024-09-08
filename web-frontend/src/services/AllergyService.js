import axios from "axios";
import { getApiUrl } from '../utils/utils';

export const getAllergyNames = () => axios.get(getApiUrl('/allergies/names'));
