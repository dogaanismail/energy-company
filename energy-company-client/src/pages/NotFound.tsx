
import { Link } from 'react-router-dom';

const NotFound = () => {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center items-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full text-center">
        <div className="flex justify-center">
          <div className="h-24 w-24 rounded-full bg-green-100 flex items-center justify-center">
            <span className="text-green-600 text-4xl">404</span>
          </div>
        </div>
        <h1 className="mt-6 text-4xl font-extrabold text-gray-900">Page Not Found</h1>
        <p className="mt-2 text-base text-gray-500">
          Sorry, we couldn't find the page you're looking for.
        </p>
        <div className="mt-8 flex justify-center">
          <Link
            to="/"
            className="inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-white bg-green-600 hover:bg-green-700"
          >
            Go back home
          </Link>
        </div>
      </div>
    </div>
  );
};

export default NotFound;