import axios from "axios";
import { getApiUrl } from '../utils/utils';

export const createUser = (user) => axios.post(getApiUrl('/user/register'), user);

export const getUserProfile = (userId) => {
    const token = localStorage.getItem('jwtToken');
    return axios.get(getApiUrl(`/user/${userId}`), {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

export const updateUserProfile = (userId, userData) => {
    const token = localStorage.getItem('jwtToken');
    return axios.put(getApiUrl(`/user/${userId}`), userData, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

export const changePassword = (userId, passwordChangeData) => {
    const token = localStorage.getItem('jwtToken');
    return axios.put(getApiUrl(`/user/${userId}/change-password`), passwordChangeData, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};
