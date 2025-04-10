import { XIcon } from '@heroicons/react/solid';

interface AlertProps {
  type: 'success' | 'error' | 'warning' | 'info';
  message: string;
  onClose?: () => void;
}

const Alert = ({ type, message, onClose }: AlertProps) => {
  const typeClasses = {
    success: 'bg-green-50 text-green-800 border-green-200',
    error: 'bg-red-50 text-red-800 border-red-200',
    warning: 'bg-yellow-50 text-yellow-800 border-yellow-200',
    info: 'bg-blue-50 text-blue-800 border-blue-200',
  };

  return (
    <div className={`rounded-md p-4 border ${typeClasses[type]} mb-4`}>
      <div className="flex">
        <div className="flex-grow">{message}</div>
        {onClose && (
          <button
            type="button"
            className="ml-auto -mx-1.5 -my-1.5 rounded-md p-1.5 inline-flex focus:outline-none"
            onClick={onClose}
          >
            <span className="sr-only">Dismiss</span>
            <XIcon className="h-5 w-5" aria-hidden="true" />
          </button>
        )}
      </div>
    </div>
  );
};

export default Alert;