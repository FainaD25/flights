<script setup>
import {computed, onMounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';

const route = useRoute();
const flightId = route.params.id;
const flight = ref(null);
const seats = ref([]);
const selectedSeats = ref([]);
const adjacentSeatsNeeded = ref(2);
const userPreferences = ref({
  windowSeat: false,
  legSpace: false,
  nearExit: false,
  adjacentSeats: false,
});

const fetchFlightDetails = async () => {
  try {
    const response = await axios.get(`/api/public/flights/${flightId}`);
    flight.value = response.data;
    seats.value = response.data.seats;
  } catch (error) {
    console.error('Error fetching flight details:', error);
  }
};

onMounted(fetchFlightDetails);

const recommendedSeats = computed(() => {
  if (!userPreferences.value.windowSeat &&
      !userPreferences.value.legSpace &&
      !userPreferences.value.nearExit &&
      !userPreferences.value.adjacentSeats) {
    return [];
  }

  let filteredSeats = seats.value.filter(seat => {
    if (seat.taken) return false;

    const windowSeatMatch = userPreferences.value.windowSeat ? seat.windowSeat : true;
    const legSpaceMatch = userPreferences.value.legSpace ? seat.legSpace : true;
    const nearExitMatch = userPreferences.value.nearExit ? seat.nearExit : true;

    return windowSeatMatch && legSpaceMatch && nearExitMatch;
  });

  if (userPreferences.value.adjacentSeats) {
    const adjacentGroups = getAdjacentSeats(adjacentSeatsNeeded.value);
    filteredSeats = adjacentGroups.flat().filter(seat => {
      return filteredSeats.some(filteredSeat => filteredSeat.id === seat.id);
    });
  }

  return filteredSeats;
});

const getAdjacentSeats = (neededCount) => {
  const adjacentGroups = [];
  const rowSeats = {};

  seats.value.forEach(seat => {
    if (seat.taken) return;
    if (!rowSeats[seat.row]) rowSeats[seat.row] = [];
    rowSeats[seat.row].push(seat);
  });

  Object.values(rowSeats).forEach(row => {
    row.sort((a, b) => a.column.localeCompare(b.column));

    let group = [];

    for (let i = 0; i < row.length; i++) {
      if (group.length === 0 || areSeatsAdjacent(group[group.length - 1], row[i])) {
        group.push(row[i]);
      } else {
        if (group.length >= 2) {
          adjacentGroups.push([...group]);
        }
        group = [row[i]];
      }
    }

    if (group.length >= 2) {
      adjacentGroups.push(group);
    }
  });

  return adjacentGroups.filter(group => group.length >= neededCount);
};



const areSeatsAdjacent = (seat1, seat2) => {
  const adjacentPairs = [
    ['A', 'B'],
    ['B', 'C'],
    ['D', 'E'],
    ['E', 'F']
  ];
  return adjacentPairs.some(pair =>
      pair.includes(seat1.column) && pair.includes(seat2.column)
  );
};

const getSeatClass = (seat) => {
  if (seat.taken) return 'seat taken';
  if (selectedSeats.value.some(s => s.id === seat.id)) return 'seat selected';
  if (recommendedSeats.value.some(recommendedSeat => recommendedSeat.id === seat.id)) return 'seat recommended';
  return 'seat available';
};

const selectSeat = (seat) => {
  if (!seat.taken && !selectedSeats.value.some(s => s.id === seat.id)) {
    selectedSeats.value.push(seat);
  } else {
    deselectSeat(seat);
  }
};

const deselectSeat = (seat) => {
  const index = selectedSeats.value.findIndex(s => s.id === seat.id);
  if (index !== -1) {
    selectedSeats.value.splice(index, 1);
  }
};

const handlePreferenceChange = () => {
  selectedSeats.value = [];
};

const bookSeats = async () => {
  if (selectedSeats.value.length === 0) {
    alert("Please select seats before booking.");
    return;
  }

  try {
    const seatIds = selectedSeats.value.map(seat => seat.id);
    const response = await axios.put(`/api/public/flights/${flightId}/book`, seatIds);

    if (response.status === 204) {
      alert("Seats booked successfully!");

      await fetchFlightDetails();

      selectedSeats.value = [];
    }
  } catch (error) {
    console.error("Error booking seats:", error);
    alert("Some seats may have already been booked.");
  }
};


</script>

<template>
  <div class="main-content">
    <h2 v-if="flight">
      Flight: {{ flight.from }} → {{ flight.to }} ({{ flight.date }})
    </h2>
    <p v-if="flight">
      Departure: {{ flight.departureTime }} | Arrival: {{ flight.arrivalTime }}
    </p>

    <!-- User Preferences -->
    <div class="preferences">
      <label>
        <input type="checkbox" v-model="userPreferences.windowSeat" @change="handlePreferenceChange" />
        Window Seat
      </label>
      <label>
        <input type="checkbox" v-model="userPreferences.legSpace" @change="handlePreferenceChange" />
        Leg Space
      </label>
      <label>
        <input type="checkbox" v-model="userPreferences.nearExit" @change="handlePreferenceChange" />
        Near Exit
      </label>
      <label>
        <input type="checkbox" v-model="userPreferences.adjacentSeats" @change="handlePreferenceChange" />
        Adjacent Seats
      </label>
      <div v-if="userPreferences.adjacentSeats">
        <label for="adjacentSeatsInput">How many adjacent seats?</label>
        <input class="adjacent-seats-container" id="adjacentSeatsInput" type="number" v-model="adjacentSeatsNeeded" min="2" max="3" />
      </div>
    </div>

    <!-- Seat Selection Info -->
    <p v-if="selectedSeats.length > 0" class="selected-info">
      Selected seats:
      <span v-for="(seat, index) in selectedSeats" :key="seat.id">
        {{ seat.id }}{{ index < selectedSeats.length - 1 ? ', ' : '' }}
      </span>
      <button @click="bookSeats" :disabled="selectedSeats.length === 0" class="confirm-button">
        Book Selected Seats
      </button>
    </p>

    <!-- Seat Legend -->
    <div class="legend">
      <div class="legend-item"><span class="seat available"></span> Available</div>
      <div class="legend-item"><span class="seat taken"></span> Taken</div>
      <div class="legend-item"><span class="seat recommended"></span> Recommended</div>
      <div class="legend-item"><span class="seat selected"></span> Selected</div>
    </div>

    <!-- Seat Grid -->
    <div class="seat-grid">
      <template v-for="(seat, index) in seats" :key="seat.id">
        <!-- Aisle between columns 3 and 4 -->
        <div v-if="index % 6 === 3" class="aisle"></div>

        <!-- Seat -->
        <div :class="getSeatClass(seat)" @click="selectSeat(seat)">
          {{ seat.id }}
        </div>

        <!-- Row Gap after row 10 -->
        <div v-if="(index + 1) % 60 === 0" class="row-gap"></div>
      </template>
    </div>
  </div>
</template>

<style scoped>
.main-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.seat-grid {
  display: grid;
  grid-template-columns: repeat(7, 50px);
  gap: 5px;
  padding: 20px;
}

.seat {
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  border-radius: 5px;
  border: 1px solid #333;
  cursor: pointer;
  transition: transform 0.2s;
}

.seat:hover {
  transform: scale(1.1);
}

.available { background-color: #4CAF50; color: white; }
.taken { background-color: #FF5252; color: white; }
.recommended { background-color: #42A5F5; color: white; }
.selected { background-color: #FFD700; color: black; }

.aisle {
  width: 20px;
}

.row-gap {
  grid-column: 1 / -1;
  height: 20px;
}

.legend {
  display: flex;
  gap: 15px;
  margin-top: 20px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.legend-item .seat {
  width: 20px;
  height: 20px;
  border-radius: 3px;
}

.preferences label {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.preferences {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 20px;
}

.preferences input[type="checkbox"] {
  transform: scale(1.2);
}

button {
  margin-left: 2vw;
  width: auto;
}
</style>
