<template>
  <div class="app-container">
    <div class="filter-container">
      <common-log :is-error-log="true"/>
    </div>
  </div>
</template>

<style>

</style>

<script>
  import CommonLog from './components/commonLog.vue'

  export default {
    name: 'BusinessLog',
    components: { CommonLog }
  }
</script>
