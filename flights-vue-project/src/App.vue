<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<template>
  <header>
    <div class="header-content">
      <img alt="Logo" class="logo" src="@/assets/logo.svg" width="125px" height="125" />
      <HelloWorld msg="Read It Later" />
    </div>

    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/about">About</RouterLink>
    </nav>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  position: sticky;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 100;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 2vw;
}

.header-content {
  display: flex;
  flex-direction: column;
  align-items: center; /* Vertically center-aligns logo and text */
  margin-left: 2vw;
}

.logo {
  display: block;
}

nav {
  width: 100%;
  font-size: 14px;
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1vw;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid skyblue;
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {

  .header-content {
    flex-direction: row;
    align-items: center; /* Vertically center-aligns logo and text */
  }

  .logo {
    margin: 0 2rem 0 0;
  }


  nav {
    text-align: left;
    margin-left: 1rem;
    font-size: 1rem;

    margin-top: 1rem;
  }
}
</style>
