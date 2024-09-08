import React from 'react';
import { Link } from 'react-router-dom';

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {
    // Update state so the next render shows the fallback UI.
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    // You can also log the error to an error reporting service
    console.error("Error caught by ErrorBoundary:", error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      // You can render any custom fallback UI
      return (
        <div className="error-page">
          <h1>Sorry, we are facing some issues with our backend!</h1>
          <p>Please continue to enjoy our app.</p>
          <Link to="/" className="btn btn-primary">Return to Home</Link>
        </div>
      );
    }

    return this.props.children; 
  }
}

export default ErrorBoundary;
