import axios from 'axios';
import { getApiUrl } from '../utils/utils';

export const submitFeedback = (feedbackData) => {
    const token = localStorage.getItem('jwtToken'); 

    return axios.post(getApiUrl('/feedback'), feedbackData, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${token}` 
        },
    });
};
