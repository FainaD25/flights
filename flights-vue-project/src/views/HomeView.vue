<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { format, parseISO } from "date-fns";


const flights = ref([]);
const errorMessage = ref(null);

const lastSearchQuery = ref({
  date: null,
  from: null,
  to: null,
  price: null,
  duration: null,
});

const getFlights = async () => {
  try {
    const params = Object.fromEntries(
        Object.entries(lastSearchQuery.value).filter(([_, v]) => v != null && v !== "")
    );

    if (params.date) {
      params.date = format(parseISO(params.date), "dd-MM-yyyy");
    }

    const response = await axios.get('/api/public/flights', { params });
    console.log("API response:", response.data);
    flights.value = response.data.length > 0 ? response.data : [];
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'An error occurred while fetching flights';
  }
};

// Filters
const filterByDate = (date) => { lastSearchQuery.value.date = date; getFlights(); };
const filterByFrom = (from) => { lastSearchQuery.value.from = from; getFlights(); };
const filterByDest = (to) => { lastSearchQuery.value.to = to; getFlights(); };
const filterByDuration = (duration) => { lastSearchQuery.value.duration = duration; getFlights(); };
const filterByPrice = (price) => { lastSearchQuery.value.price = price; getFlights(); };

const router = useRouter();
const redirectToSeats = (flightId) => {
  router.push(`/seats/${flightId}`);
};

onMounted(() => {
  getFlights();
});
</script>


<template>
  <div class="page-layout">
    <main class="main-content">
      <div class="search-container">
        <h3>Search Flights</h3>
        <input type="text" v-model="lastSearchQuery.from" placeholder="From (e.g., Tallinn)" />
        <input type="text" v-model="lastSearchQuery.to" placeholder="To (e.g., London)" />
        <input type="date" v-model="lastSearchQuery.date" />
        <input type="number" v-model="lastSearchQuery.duration" placeholder="Duration (hours)" />
        <input type="number" v-model="lastSearchQuery.price" placeholder="Max Price ($)" />
        <button @click="getFlights">Search</button>
      </div>


      <div class="flights">
        <h2>Flights</h2>
        <table v-if="flights.length" class="flight-table">
          <thead>
          <tr>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Date</th>
            <th>Duration</th>
            <th>Price</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="flight in flights" :key="flight.id" @click="redirectToSeats(flight.id)" style="cursor: pointer;">
            <td>{{ flight.from }}</td>
            <td>{{ flight.to }}</td>
            <td>{{ flight.date }}</td>
            <td>{{ flight.duration }}h</td>
            <td>${{ flight.price }}</td>
          </tr>
          </tbody>
        </table>
        <p v-else class="no-flights-message">No flights found</p>
        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      </div>
    </main>
  </div>
</template>


<style scoped>
.page-layout {
  display: flex;
  align-items: flex-start; /* Align content to the top */
}

/* Sidebar */
.sidebar {
  width: 25vw;
  background-color: skyblue;
  padding: 1rem;
  border-right: 1px solid skyblue;
  top: 0;
  left: 0;
  transition: width 0.3s ease;
  position: relative;
}

.sidebar.collapsed {
  width: 5vw; /* Adjust as needed */
  border: none;
}
.sidebar .toggle-button {
  position: absolute;
  top: 10px;
  right: -40px; /* Adjust to fit button appearance */
  background-color: transparent;
  border: none;
  color: skyblue;
  border-radius: 50%;
  width: 50px;
  height: 30px;
  font-size: 18px;
  cursor: pointer;
}

.sidebar.collapsed .toggle-button {
  right: -5vw; /* Adjust position for collapsed state */
}

.sidebar h3 {
  margin-bottom: 1rem;
  font-size: 1.25rem;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: opacity 0.3s ease;
}


.sidebar.collapsed ul {
  opacity: 0;
  pointer-events: none;
}

.sidebar li {
  padding: 0.5vw 1rem;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.sidebar li:hover {
  background-color: skyblue;
  color: white;
}

.sidebar li.active {
  background-color: skyblue;
  color: white;
}

/* Main Content */
.main-content {
  flex: 1; /* Take up available space next to the sidebar */
  align-items: center; /* Center the content horizontally within the main content area */
  padding: 1rem;
  margin: 0 auto; /* Center the content horizontally if there's extra space */
}

.search-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  align-items: center;
  justify-content: center;
  padding: 2vw;
  border: 1px solid skyblue;
  border-radius: 8px;
  margin: 0 auto 2vw auto;
  width: 80%;
}

.search-bar {
  display: flex;
  gap: 10px;
}

.search-bar input {
  padding: 0.5rem;
  width: 30vw;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-options {
  display: flex;
  gap: 0.5rem;
}

.search-options label {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  background-color: skyblue;
  color: white;
  transition: all 0.3s ease;
}

.search-options label.active {
  background-color: skyblue;
  color: white;
  border-color: #0056b3;
}

.search-options input[type="radio"] {
  display: none; /* Hide radio buttons */
}

.search-button {
  width: 100px
}

/* Dropdown Menus */
.dropdown-menus {
  display: flex;
  gap: 1rem;
  align-items: center;
  margin-top: 1rem;
}

.dropdown-menus label {
  font-weight: bold;
}

.dropdown-menus select {
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 1rem;
}

.books {
  max-width: 80%;
  margin: 0 auto;
  padding: 1rem;
  border: 1px solid skyblue;
  border-radius: 4px;
}

.books h2 {
  text-align: center;
}

.books div {
  margin-bottom: 1rem;
}

.pagination {
  width: 80%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem; /* Space between elements */
  margin: 1rem auto;
}

.page-number {
  display: inline-block;
  margin: 0 10px;
  text-align: center;
}

.books label {
  display: block;
  margin-bottom: 0.5rem;
}

.book-row{
  transition: background-color 0.3s ease;
}

.book-row:hover {
  background-color: skyblue;
}

.button-column {
  width: 25%;
}

</style>

