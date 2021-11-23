<template>
  <div>
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-menu mode="vertical" :default-active="$route.path"
             :collapse="isCollapse"
             background-color="#304156"
             text-color="#bfcbd9"
             active-text-color="#409EFF"
             :collapse-transition="false">
      <sidebar-item :routes="permissionRouters"/>
    </el-menu>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Logo from './Logo'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem, Logo },
  computed: {
    ...mapGetters([
      'permissionRouters',
      'sidebar'
    ]),
    showLogo() {
      return true;
    },
    isCollapse() {
      return !this.sidebar.opened;
    }
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .el-menu {
    min-height: 100%;
    border-right-width: 0;
  }
</style>
