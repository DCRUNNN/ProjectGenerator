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
    path: '/generator',
    component: Layout,
    redirect: '/generator/index',
    icon: 'guide',
    noDropDown: true,
    children: [
      {
        path: 'index',
        name: '创建工程',
        component: () => import('@/views/generator/index'),
      },
    ]
  },
  {
    path: '/template',
    component: Layout,
    name: '模板管理',
    redirect: '/template/index',
    icon: 'form',
    children: [
      {
        path: 'create',
        name: '新建模板',
        component: () => import('@/views/template/create'),
        meta: { permission: ['template:insert'] },
        icon: 'edit',
      },
      {
        path: 'edit/:id(\\d+)',
        name: '编辑模板',
        component: () => import('@/views/template/edit'),
        meta: { permission: ['template:update'] },
        icon: 'edit',
        hidden: true
      },
      {
        path: 'index',
        name: '模板列表',
        component: () => import('@/views/template/index'),
        meta: { permission: ['template:list'] },
        icon: 'list',
      },
    ]
  },
  {
    path: '/param',
    component: Layout,
    redirect: '/param/index',
    icon: 'shopping',
    noDropDown: true,
    children: [
      {
        path: 'index',
        name: '参数管理',
        component: () => import('@/views/param/index'),
        meta: { permission: ['params:list'] }
      },
    ]
  },
  {
    path: '/log',
    component: Layout,
    redirect: '/log/businessLog',
    name: '系统监控',
    icon: 'chart',
    children: [
      {
        path: 'businessLog',
        name: '业务日志 ',
        component: () => import('@/views/log/bizLog'),
        icon: 'documentation'
      },
      {
        path: 'errorLog',
        name: '异常日志',
        component: () => import('@/views/log/errorLog'),
        icon: 'bug'
      }
    ]
  },
]
