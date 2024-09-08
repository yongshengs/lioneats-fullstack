import axios from 'axios';
import { getApiUrl } from '../utils/utils';

export const uploadImage = (file) => {
    const formData = new FormData();
    formData.append('file', file);

    const token = localStorage.getItem('jwtToken');

    return axios.post(getApiUrl('/upload'), formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Accept': 'application/json',
            'Authorization': `Bearer ${token}` 
        },
    });
};
