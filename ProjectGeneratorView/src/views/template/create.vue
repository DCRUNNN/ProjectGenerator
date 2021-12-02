<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="components-container">
        <common-template :is-edit="false"/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>

<script>
  import CommonTemplate from './components/commonTemplate'

  let id = 1000;
  export default {
    components: { CommonTemplate },
    computed: {},
    watch: {},
    data() {
      return {

      }
    },
    mounted() {

    },
    created() {

    },
    methods: {

    }
  }
</script>
