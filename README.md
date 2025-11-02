# Movie Booking Application

A comprehensive REST API-based Movie Booking System built with Spring Boot that allows users to browse movies, book tickets, and manage theater operations. The application features JWT-based authentication, role-based access control (Admin/User), and a robust booking system with seat management.

## 🎯 Features

### Core Functionality
- **User Authentication & Authorization**
  - User registration and login
  - JWT-based authentication
  - Role-based access control (ADMIN, USER)
  
- **Movie Management**
  - Browse all movies
  - Search movies by genre, language, or title
  - Admin can add, update, and delete movies

- **Theater Management**
  - View theaters by location
  - Manage theater information (Admin only)
  
- **Show Management**
  - View shows by theater or movie
  - Admin can create and manage shows

- **Booking System**
  - Book tickets for shows
  - Seat selection and validation
  - View booking history
  - Update booking status (CONFIRMED, PENDING, CANCELED)

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Additional Libraries**:
  - JWT (jjwt 0.11.5)
  - Lombok
  - Spring Boot DevTools

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd MovieBookingApplication
```

### 2. Database Setup

1. Create a MySQL database (or let the application create it automatically):
   ```sql
   CREATE DATABASE MovieBookingApp;
   ```

2. Update database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/MovieBookingApp?createDatabaseIfNotExist=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Update JWT secret key in `application.properties`:
   ```properties
   jwt.secretKey=YourSuperSecureSecretKeyHere
   jwt.expiration=86400000
   ```

### 3. Build the Project

Using Maven wrapper:
```bash
./mvnw clean install
```

Or using Maven directly:
```bash
mvn clean install
```

### 4. Run the Application

Using Maven wrapper:
```bash
./mvnw spring-boot:run
```

Or using Maven directly:
```bash
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

## 📁 Project Structure

```
MovieBookingApplication/
├── src/
│   ├── main/
│   │   ├── java/com/example/MovieBookingApplication/
│   │   │   ├── Config/              # Security configuration
│   │   │   ├── Controllers/         # REST API endpoints
│   │   │   ├── dtos/                # Data Transfer Objects
│   │   │   ├── Entities/            # JPA entities
│   │   │   ├── jwt/                 # JWT authentication filter
│   │   │   ├── Repositories/        # Data access layer
│   │   │   └── services/            # Business logic layer
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Test files
└── pom.xml                          # Maven configuration
```

## 🔐 API Endpoints

### Authentication (`/api/auth`)
- `POST /api/auth/registerNormalUser` - Register a new user
- `POST /api/auth/login` - User login (returns JWT token)

### Admin (`/api/admin`) - Requires ADMIN role
- `POST /api/admin/registerAdminUser` - Register a new admin user
- `POST /api/admin/login` - Admin login

### Movies (`/api/movies`)
- `GET /api/movies/getAllMovies` - Get all movies (Public)
- `GET /api/movies/getMoviesByGenre?genre={genre}` - Search by genre (Public)
- `GET /api/movies/getMoviesByLanguage?language={language}` - Search by language (Public)
- `GET /api/movies/getMoviesByTitle?name={title}` - Search by title (Public)
- `POST /api/movies/addMovie` - Add movie (Admin only)
- `PUT /api/movies/updateMovie/{id}` - Update movie (Admin only)
- `DELETE /api/movies/deleteMovie/{id}` - Delete movie (Admin only)

### Theaters (`/api/theater`)
- `GET /api/theater/getTheaterByLocation?location={location}` - Get theaters by location (Public)
- `POST /api/theater/addTheater` - Add theater (Admin only)
- `PUT /api/theater/updateTheater/{id}` - Update theater (Admin only)
- `DELETE /api/theater/deleteTheater/{id}` - Delete theater (Admin only)

### Shows (`/api/show`)
- `GET /api/show/getAllShows` - Get all shows (Public)
- `GET /api/show/getShowsByTheater/{id}` - Get shows by theater ID (Public)
- `GET /api/show/getShowsByMovie/{id}` - Get shows by movie ID (Public)
- `POST /api/show/addShow` - Add show (Admin only)
- `PUT /api/show/updateShow/{id}` - Update show (Admin only)
- `DELETE /api/show/deleteShow/{id}` - Delete show (Admin only)

### Bookings (`/api/booking`)
- `GET /api/booking/getAllBookings` - Get all bookings
- `GET /api/booking/getUserBooking/{userId}` - Get bookings by user ID
- `GET /api/booking/getShowBooking/{showId}` - Get bookings by show ID
- `GET /api/booking/getBookingsByStatus/{bookingStatus}` - Get bookings by status (CONFIRMED, PENDING, CANCELED)
- `POST /api/booking/addBooking` - Create a new booking
- `PUT /api/booking/updateBooking/{id}` - Update booking
- `PUT /api/booking/updateShowStatus/{id}?bookingStatus={status}` - Update booking status
- `DELETE /api/booking/deleteBooking/{id}` - Delete booking

## 🔑 Authentication

The application uses JWT (JSON Web Tokens) for authentication. After successful login:

1. You'll receive a JWT token in the response
2. Include this token in the `Authorization` header for subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

### Example Login Request
```json
POST /api/auth/login
Content-Type: application/json

{
  "username": "user123",
  "password": "password123"
}
```

### Example Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "user123"
}
```

## 📊 Database Schema

### Entities and Relationships

- **User**: Stores user information and authentication details
  - One-to-Many relationship with Booking
  
- **Movie**: Movie information (name, description, genre, duration, etc.)
  - One-to-Many relationship with Show

- **Theater**: Theater information (name, location, capacity, screen type)
  - One-to-Many relationship with Show

- **Show**: Represents a movie showing at a specific theater and time
  - Many-to-One with Movie
  - Many-to-One with Theater
  - One-to-Many with Booking

- **Booking**: Ticket booking information
  - Many-to-One with User
  - Many-to-One with Show
  - Status: CONFIRMED, PENDING, CANCELED

## 🧪 Testing

Run tests using Maven:
```bash
./mvnw test
```

## 🔧 Configuration

Key configuration options in `application.properties`:

- **Database**: Configure MySQL connection
- **JWT**: Set secret key and token expiration time
- **JPA/Hibernate**: 
  - `spring.jpa.hibernate.ddl-auto=update` - Auto-update schema
  - `spring.jpa.show-sql=true` - Log SQL queries

## 🛡️ Security Features

- **Password Encryption**: BCrypt password hashing
- **JWT Authentication**: Stateless authentication
- **Role-Based Access Control**: ADMIN and USER roles
- **CSRF Protection**: Disabled for API (can be enabled for web)
- **SQL Injection Protection**: Parameterized queries via JPA

## 📝 Usage Examples

### Register a User
```bash
curl -X POST http://localhost:8080/api/auth/registerNormalUser \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "securePassword123",
    "email": "john@example.com"
  }'
```

### Login and Get Token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "securePassword123"
  }'
```

### Create a Booking (with JWT token)
```bash
curl -X POST http://localhost:8080/api/booking/addBooking \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "userId": 1,
    "showId": 1,
    "numberOfSeats": 2,
    "seatsNumbers": ["A1", "A2"]
  }'
```

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database exists or `createDatabaseIfNotExist=true` is set

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`

3. **JWT Token Invalid**
   - Ensure token is included in `Authorization: Bearer <token>` header
   - Check token hasn't expired (default: 24 hours)

4. **Access Denied**
   - Verify user has correct role (ADMIN or USER)
   - Check JWT token is valid and not expired

## 📄 License

This project is provided as-is for demonstration purposes.

## 👥 Support

For issues or questions, please contact the development team.

## 🔄 Version History

- **v0.0.1-SNAPSHOT**: Initial release
  - Basic CRUD operations for Movies, Theaters, Shows, and Bookings
  - JWT authentication
  - Role-based access control
  - Seat booking validation

---

**Built with ❤️ using Spring Boot**

