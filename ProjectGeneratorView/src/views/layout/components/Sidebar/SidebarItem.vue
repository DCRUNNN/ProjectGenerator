<template>
  <div>
    <template v-for="item in routes">
      <router-link v-if="!item.hidden && item.noDropDown && item.children.length > 0"
                   :to="item.path + '/' + item.children[0].path">
        <el-menu-item :index="item.path + '/' + item.children[0].path">
          <icon-svg v-if="item.icon" :icon-class="item.icon"/>
          <!--没有子目录-->
          {{ item.children[0].name }}
        </el-menu-item>
      </router-link>
      <el-submenu :index="item.name" v-if="!item.noDropDown && !item.hidden">
        <template slot="title">
          <icon-svg v-if="item.icon" :icon-class="item.icon"/>
          <!--有子目录的目录项的根目录-->
          {{ item.name }}
        </template>
        <template v-for="(child,index) in item.children" v-if="!child.hidden">
          <sidebar-item class="menu-indent" v-if="child.children && child.children.length > 0" :routes="[child]" :key="index"/>
          <router-link v-else class="menu-indent" :to="item.path + '/' + child.path" :key="index">
            <el-menu-item :index="item.path + '/' + child.path">
              <icon-svg v-if="child.icon" :icon-class="child.icon"/>
              {{ child.name }}
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>
    </template>
  </div>
</template>

<script>
export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .svg-icon {
    margin-right: 10px;
  }

  .hideSidebar .menu-indent {
    display: block;
    text-indent: 10px;
  }
</style>
