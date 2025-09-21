# Seismic Sensor Dashboard

A Spring Boot application for viewing and managing seismic sensors with a web-based dashboard. 

Since this assessment is very similar to my master's professional project, I have implemented both table and map view functions

This project is merely a monolithic program and does not implement front-end and back-end separation. If the front end needs to handle multiple devices, it would be easier to maintain by separating the front-end code separately
## Technical Stack

- **Backend API (Java/Spring Boot)**: REST endpoints for sensor data and maintenance mode toggling
- **Frontend Dashboard (html/js)**: Table and map views for sensor visualization
- **Leaflet Integration**: Interactive map using Leaflet and OpenStreetMap with custom markers

## API Endpoints

- `GET /api/sensors` - Returns a list of all sensors, but in practice we should conduct queries by page
- `POST /api/sensors/{id}/maintenance` - Toggles maintenance mode for a specific sensor


## Running the Application

1. **Prerequisites**: Java 21 and Maven installed

2. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Run Tests**:
   ```bash
   mvn test
   ```

4. **Access the Dashboard**: Open your browser and navigate to `http://localhost:8080`

