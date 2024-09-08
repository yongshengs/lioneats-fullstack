import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { uploadImage } from '../services/ImageService';
import { submitFeedback } from '../services/MLFeedbackService';
import { getAllDishes } from '../services/DishDetailService';
import './styles/UploadImageComponent.css';

const UploadImageComponent = () => {
    const [file, setFile] = useState(null);
    const [fileName, setFileName] = useState('Choose image');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [predictedDish, setPredictedDish] = useState('');
    const [predictedDishId, setPredictedDishId] = useState(''); // State to hold the predicted dish ID
    const [imageUrl, setImageUrl] = useState('');
    const [dishesList, setDishesList] = useState([]);
    const [selectedDish, setSelectedDish] = useState('');
    const [remarks, setRemarks] = useState('');
    const [dragging, setDragging] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        getAllDishes()
            .then(response => {
                // console.log("Dishes retrieved from getAllDishes:", response.data); // Log the dishes data
                setDishesList(response.data);
            })
            .catch(error => console.error('Error fetching dishes:', error));
    }, []);

    const handleFileChange = (e) => {
        const files = e.target.files;
        if (files.length > 0) {
            setFile(files[0]);
            setFileName(files[0].name);
        }
    };

    const handleDrop = (e) => {
        e.preventDefault();
        setDragging(false);
        const files = e.dataTransfer.files;
        if (files.length > 0) {
            setFile(files[0]);
            setFileName(files[0].name);
        }
    };

    const handleDragOver = (e) => {
        e.preventDefault();
        setDragging(true);
    };

    const handleDragLeave = () => {
        setDragging(false);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!file) {
            setError('Please select a file to upload.');
            return;
        }
    
        setLoading(true);
        setError('');
    
        try {
            const response = await uploadImage(file);
            // console.log("Response after image upload:", response.data); // Log the response from the upload
            setImageUrl(response.data.imageUrl);
            setPredictedDish(response.data.predictedDish);
    
            // Find the ID of the predicted dish from the dishes list
            const dish = dishesList.find(d => d.dishDetailName === response.data.predictedDish);
            // console.log("Predicted dish:", response.data.predictedDish); // Log the predicted dish name
            // console.log("Dish ID found:", dish ? dish.id : ''); // Log the ID of the predicted dish
            setPredictedDishId(dish ? dish.id : ''); // Set the dish ID if found
        } catch (error) {
            setError('Image upload failed. Please try again.');
        } finally {
            setLoading(false);
        }
    };
    

    const handleFeedbackSubmit = async (e) => {
        e.preventDefault();
        if (!selectedDish) {
            setError('Please select a dish.');
            return;
        }

        try {
            await submitFeedback({
                imageLocation: imageUrl,
                ml_result: predictedDish,
                userDish: selectedDish,
                remarks: remarks,
            });
            navigate('/upload_success');
        } catch (error) {
            setError('Feedback submission failed. Please try again.');
        }
    };

    const handleViewDish = () => {
        navigate(`/dishes/${predictedDishId}`);
    };

    return (
        <div className="container mt-5">
            <h1>Upload Image</h1>
            <div 
                className={`form-group drop-area ${dragging ? 'dragging' : ''}`}
                onDragOver={handleDragOver}
                onDragLeave={handleDragLeave}
                onDrop={handleDrop}
                onClick={() => document.getElementById('file').click()}
            >
                <label htmlFor="file" id="fileLabel">
                    {fileName}
                </label>
                <input
                    type="file"
                    id="file"
                    name="file"
                    onChange={handleFileChange}
                    className="d-none"
                />
                <p>Drag and drop your image here or click to select</p>
            </div>
            <button className="btn btn-primary" onClick={handleSubmit} disabled={loading}>
                {loading ? 'Uploading...' : 'Upload'}
            </button>
            {error && <div className="alert alert-danger mt-3">{error}</div>}
            {imageUrl && (
                <div className="mt-5">
                    <h2>Result</h2>
                    <img src={imageUrl} alt="Uploaded Dish" style={{ maxWidth: '100%', height: 'auto' }} />
                    <p>We think your dish is: {predictedDish}</p>
                    {predictedDishId && (
                        <button className="btn btn-info mt-3" onClick={handleViewDish}>
                            View {predictedDish} Details
                        </button>
                    )}
                    <form onSubmit={handleFeedbackSubmit}>
                        <div className="form-group">
                            <strong>Wrong dish? Let us know!</strong><br/><br/>
                            <label htmlFor="userDish">What the dish actually is:</label>
                            <select
                                id="userDish"
                                name="userDish"
                                className="form-control"
                                value={selectedDish}
                                onChange={(e) => setSelectedDish(e.target.value)}
                                required
                            >
                                <option value="">Select Dish</option>
                                {dishesList.map(dish => (
                                    <option key={dish.id} value={dish.dishDetailName}>
                                        {dish.dishDetailName}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="remarks">Remarks:</label>
                            <input
                                type="text"
                                id="remarks"
                                name="remarks"
                                className="form-control"
                                value={remarks}
                                onChange={(e) => setRemarks(e.target.value)}
                            />
                        </div>
                        <button type="submit" className="btn btn-primary">Submit Feedback</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default UploadImageComponent;
