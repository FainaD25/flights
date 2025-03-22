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
  departureTime: null,
  arrivalTime: null,
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


const router = useRouter();
function redirectToSeats(id) {
  router.push(`/flights/${id}`);
}

onMounted(() => {
  getFlights();
});
</script>


<template>
  <div class="page-layout">
    <main class="main-content">
      <div class="search-container">
        <h3>Where are you heading today?</h3>
        <div class="locations">
          <input type="text" v-model="lastSearchQuery.from" placeholder="From (e.g., Tallinn)" />
          <input type="text" v-model="lastSearchQuery.to" placeholder="To (e.g., London)" />
        </div>
        <div class="time">
          <div>
            <label for="date">Date</label>
            <input type="date" v-model="lastSearchQuery.date"/>
          </div>
          <div>
            <label for="departureTime">Departure Time</label>
            <input type="time" v-model="lastSearchQuery.departureTime" placeholder="Departure Time" />
          </div>
          <div>
            <label for="arrivalTime">Arrival Time</label>
            <input type="time" v-model="lastSearchQuery.arrivalTime" placeholder="Arrival Time" />
          </div>
        </div>

        <div>
        <input class="price" type="number" v-model="lastSearchQuery.price" placeholder="Max Price ($)" />
        </div>
        <div class="search">
        <button type="button" class="confirm-button" @click="getFlights">Search</button>
        </div>
      </div>


      <div class="flights">
        <h2>Flights</h2>
        <table v-if="flights.length" class="flight-table">
          <thead>
          <tr>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Date</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Price</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="flight in flights" :key="flight.id" @click="redirectToSeats(flight.id)" style="cursor: pointer;">
            <td>{{ flight.from }}</td>
            <td>{{ flight.to }}</td>
            <td>{{ flight.date }}</td>
            <td>{{ flight.departureTime }}</td>
            <td>{{ flight.arrivalTime }}</td>
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
  align-items: flex-start;
}

.main-content {
  align-items: center;
  margin: 0 auto;
}

.search-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1vw;
  align-items: center;
  justify-content: center;
  padding: 2vw;
  border: 1px solid skyblue;
  border-radius: 8px;
  margin: 0 auto 2vw auto;
  width: 80%;
}

.locations {
  display: flex;
  gap: 1vw;
}

.time {
  display: flex;
  gap: 1vw;
  width: 100%;
  justify-content: center;
}

.price {
  width: 100%;
}
.search {
  display: flex;
  width: 100%;
  justify-content: center;
}


.flights h2 {
  text-align: center;
}

.flights div {
  margin-bottom: 1rem;
}

.flight-table {
  column-gap: 1vw;
}

@media (min-width: 1024px) {
  .main-content {
    flex: 1;
    padding: 1rem 8rem 8rem;
  }

  .search-container {
    gap: 10px;
  }
}
</style>

