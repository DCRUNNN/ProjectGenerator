import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)

Vue.use(Router)

/**
 * icon : the icon show in the sidebar
 * hidden : if `hidden:true` will not show in the sidebar
 * redirect : if `redirect:noRedirect` will not redirect in the levelBar
 * noDropDown : if `noDropDown:true` will not has submenu in the sidebar
 * meta : `{ permission: ['a:xx'] }`  will control the page permission
 **/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: '/dashboard',
    icon: 'dashboard',
    noDropDown: true,
    children: [{
      path: 'dashboard',
      name: '首页',
      component: _import('dashboard/index'),
      meta: { title: 'dashboard', noCache: true }
    }]
  }
]

export default new Router({
  //mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/template',
    component: Layout,
    redirect: '/template/index',
    icon: 'documentation',
    noDropDown: true,
    children: [
      {
        path: 'index',
        name: '模板管理',
        component: () => import('@/views/template/index'),
        meta: { permission: ['template:list'] }
      },
    ]
  },
  {
    path: '/file',
    component: Layout,
    redirect: '/file/index',
    name: '文件管理',
    icon: 'documentation',
    children: [
      {
        path: 'index',
        name: '文件列表 ',
        component: () => import('@/views/file/index'),
        icon: 'list'
      },
      {
        path: 'upload',
        name: '上传文件',
        component: () => import('@/views/file/upload2'),
        icon: 'form'
      }
    ]
  },
  {
    path: '/log',
    component: Layout,
    redirect: '/log/businessLog',
    name: '系统监控',
    icon: 'documentation',
    children: [
      {
        path: 'businessLog',
        name: '业务日志 ',
        component: () => import('@/views/log/bizLog'),
        icon: 'list'
      },
      {
        path: 'errorLog',
        name: '异常日志',
        component: () => import('@/views/log/errorLog'),
        icon: 'form'
      }
    ]
  },
]
