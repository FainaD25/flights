# Flights

## Overview

This is an application built with Java, Gradle and Vue. This README will guide you through the steps to build and run the application.

## Used technologies

* Vue.js - frontend
* Spring Boot - backend
* Gradle - build tool
* Lombok
* Mapstruct

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 21
- Gradle
- NPM
- NodeJS

## How to Build and Run the Application

1. Clone the repository

### Backend

2. Run the application using Gradle:
```
./gradlew clean build
java -jar .\build\libs\flights-0.0.1-SNAPSHOT.jar
```
3. Access the backend application at http://localhost:8080

### Frontend

4. Run the Vue project using npm

```
cd flights-vue-project
npm install
npm run build
npm run dev
```

5. Access the frontend application at http://localhost:5173.


## Development

This project was developed in five days, with the backend primarily built by me. 
Since my experience in frontend development is limited — 
my only frontend project being Read It Later — 
I used it as a reference while working on this project's frontend. 
Additionally, I occasionally relied on LLMs (ChatGPT and Gemini) to help identify and fix errors when needed.


## Functionality
Here's how the functionality is implemented

### Backend
1. Database <br>
   For simplicity, this project uses a hardcoded list of flight objects instead of a database. <br> <br>
2. Flight Class <br>
   Defines a flight object, storing key flight details. <br>
function `markRandomSeatsAsTaken()` - Uses `Random` to generate a number between 0 and half of the total seats.
 Randomly selects seats based on this number and marks them as taken <br> <br>
3. Seat Class <br>
   Defines a seat object, storing seat-related details. <br>
4. Service Layer <br>
   Handles most of the backend logic. <br>
function `getFlights` - Retrieves all flights, filtered by given parameters.
   In a real database setup, pagination and specifications would be necessary to avoid fetching the entire dataset.
   Since the dataset consists of only 10 elements, fetching all flights is manageable. <br>
function `getFlight` -  Returns details of a specific flight by its ID. <br>
function `bookSeats` -  Finds a flight by ID, locates the specified seats, and marks them as booked. <br> <br>
5. Controller <br>
After running the application, the documentation can be seen at http://localhost:8080/swagger-ui.html

### Frontend
#### `HomeView` - Flight Search Functionality
Stores all entered search parameters in `lastSearchQuery.`
When the search button is pressed, `lastSearchQuery` is sent to the backend via a GET request as a request parameter.
The flights returned by the backend are displayed in a table.
Clicking on a flight redirects the user to the `SeatView` for seat selection.

#### `SeatView` - Seat Visualization, Recommendations, and Booking
Each seat is assigned a class (taken, available, recommended, selected), which determines its color on the page.
A legend is displayed to explain the color codes.
Users can select preferences via checkboxes at the top of the page.
Adjacent seats can only be selected in groups of 2 or 3, 
as seats separated by an aisle or positioned behind each other are not considered adjacent.
The selected preferences are stored in `userPreferences`.
When a preference is chosen:
1. All matching seats are collected into a list, assigned the recommended class, and highlighted in blue.
2. If the user selects both adjacent seats and another filter, the system recommends seats that meet both criteria. <br>
Clicking on a seat marks it as selected (turns yellow).
A list of selected seats appears at the top, along with a "Book Seats" button.
Pressing the "Book Seats" button sends a request to the backend, and the booked seats turn red.

## Improvements

1. Database <br>
Currently, this project does not have a configured database. 
Instead, all flight information is stored in a hardcoded list of flight objects. 
This approach was chosen to simplify setup, as it eliminates the need for a database to run the project. 
However, in a real-world application, hardcoding values is not viable, and a proper database would be required. 
A database implementation can be seen in my other project: https://github.com/FainaD25/Read-It-Later-backend.
2. Validation and error handling <br>
Although the current implementation does not require database validation, input validation from the frontend is necessary. 
For instance, users should not be able to search for flights with a negative ticket price. 
Additionally, implementing a global error handler with clearer error messages would enhance the overall user experience. 
Examples of input validation can be found in the Read It Later - Backend project, specifically in the DtoIn classes, 
while error handling can be found in the exceptions package.
3. Using LocalDate and LocalTime instead of strings
Currently, flight date and time information is stored as strings. 
To enable time-based filtering, these strings are converted to LocalTime objects within the service layer. 
A more efficient approach would be to store these fields as LocalDate and LocalTime from the beginning.
4. User-friendly frontend design <br>
Since my experience with frontend development is limited, there is room for improvement in designing a more user-friendly interface. 
Enhancing my frontend skills would allow for a more polished and intuitive user experience.
5. Testing <br>
Unit tests and integration tests to ensure everything works as it should. 
An example can be found in the Read It Later - Backend project. 