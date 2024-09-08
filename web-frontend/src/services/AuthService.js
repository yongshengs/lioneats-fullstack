import axios from "axios";
import { getApiUrl } from '../utils/utils';

export const loginUser = async (user) => {
    const response = await axios.post(getApiUrl('/auth/login'), user);
    return response;
};

export const logoutUser = () => axios.get(getApiUrl('/auth/logout'));
