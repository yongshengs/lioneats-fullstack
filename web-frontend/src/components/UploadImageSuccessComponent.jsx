import React from 'react';
import { useNavigate } from 'react-router-dom';

const UploadImageSuccessComponent = () => {
    const navigate = useNavigate();

    const handleUploadNewImage = () => {
        navigate('/upload');
    };

    const handleGoToFeed = () => {
        navigate('/feed'); 
    };

    return (
        <div className="container mt-5">
            <h1>Feedback Submitted Successfully</h1>
            <br/>
            <p>Thank you for your feedback. We will use it to improve our model.</p>
            <button className="btn btn-primary" onClick={handleUploadNewImage}>Upload Another Image</button>
            <br/>
            <br/>
            <button className="btn btn-secondary ml-3" onClick={handleGoToFeed}>Go to Feed</button>
        </div>
    );
};

export default UploadImageSuccessComponent;
