import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      {path: 'home', name: '首页', component: () => import('../views/Home.vue')},
      {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
      {path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
