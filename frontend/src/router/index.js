import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '../views/home/HomePage.vue'
import LoginPage from "@/views/user/LoginPage.vue";
import JoinPage from "@/views/user/JoinPage.vue";
import EmailJoinPage from "@/views/user/EmailJoinPage.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: HomePage
  },
  {
    path: '/loginPage',
    name: 'LoginPage',
    component: LoginPage
  },
  {
    path: '/registerPage',
    name: 'JoinPage',
    component: JoinPage
  },{
    path: '/user/emailRegister',
    name: 'EmailJoinPage',
    component: EmailJoinPage
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
