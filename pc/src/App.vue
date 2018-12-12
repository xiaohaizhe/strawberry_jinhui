<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  body{
    margin: 0;
  }
#app {
  background: url("./../static/background.png");
  background-size: 100% 100%;
  height: 100vh;
  width: 100vw;
  margin: 0;
  padding: 0;
}
</style>
