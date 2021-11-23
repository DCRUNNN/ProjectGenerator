<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      <h4>{{welcomeText}}，{{username}}</h4>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'dashboard',
  computed: {
    ...mapGetters([
      'username'
    ]),
    welcomeText: function() {
      let timeNow = new Date();
      // 获取当前小时
      let hours = timeNow.getHours();
      if (hours >= 0 && hours <= 10) {
        return '早上好';
      } else if (hours > 10 && hours <= 14) {
        return '中午好';
      } else if (hours > 14 && hours <= 17) {
        return '下午好';
      } else if (hours > 17 && hours <= 18) {
        return '傍晚好';
      } else {
        return '晚上好';
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }
    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
