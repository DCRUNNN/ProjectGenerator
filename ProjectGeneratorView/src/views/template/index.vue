<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button>click me</el-button>

    </div>
  </div>
</template>

<style scoped>

</style>

<script>
  import { getAllProjects } from '@/api/project'
  import { getProjectPhaseList , updatePhase , deletePhase } from '@/api/projectPhase'
  import { getInvestCustomersByProjectId } from '@/api/projectCustomer';

  export default {
    components: {  },
    computed: {

    },
    watch:{

    },
    data() {
      return {

      }
    },
    mounted() {

    },
    methods:{

    }
  }
</script>
