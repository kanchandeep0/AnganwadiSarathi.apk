# Anganwadi Management System

A modern web application for managing school attendance for workers, helpers, and students.

## Features

- User management (workers, helpers, students)
- Daily attendance tracking
- Role-based access control
- Attendance reports and analytics
- Modern and responsive UI

## Tech Stack

- Next.js 13+ with App Router
- TypeScript
- MongoDB with Mongoose
- TailwindCSS
- NextAuth.js for authentication
- React Hot Toast for notifications

## Prerequisites

- Node.js 16.8 or later
- MongoDB installed and running locally
- npm or yarn package manager

## Getting Started

1. Clone the repository:
bash
git clone <repository-url>
cd school-management


2. Install dependencies:
bash
npm install


3. Create a .env.local file in the root directory with the following variables:

MONGODB_URI=mongodb://localhost:27017/school_management
NEXTAUTH_URL=http://localhost:3000
NEXTAUTH_SECRET=your-secret-key-here


4. Start the development server:
bash
npm run dev


5. Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Project Structure

- /src/app - Next.js 13 app directory with routes and API endpoints
- /src/components - Reusable React components
- /src/models - MongoDB models and schemas
- /src/lib - Utility functions and database connection
- /public - Static assets

## Contributing

1. Fork the repository
2. Create your feature branch (git checkout -b feature/amazing-feature)
3. Commit your changes (git commit -m 'Add some amazing feature')
4. Push to the branch (git push origin feature/amazing-feature)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
