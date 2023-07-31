import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '../views/home/HomePage.vue'
import LoginPage from "@/views/user/LoginPage.vue";
import JoinPage from "@/views/user/JoinPage.vue";
import EmailJoinPage from "@/views/user/EmailJoinPage.vue";
import OauthPage from "@/views/home/OauthPage.vue";
import SnsRegisterPage from "@/views/user/SnsRegisterPage.vue";
import SearchIdPwPage from "@/views/user/search/SearchIdPwPage.vue";

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
  },
  {
    path: '/user/emailRegister',
    name: 'EmailJoinPage',
    component: EmailJoinPage
  },
  {
    path: '/oauth2/redirect',
    name: 'OathPage',
    component: OauthPage
  },
  {
    path: '/snsRegister',
    name: 'SnsRegisterPage',
    component: SnsRegisterPage
  },
  {
    path: '/searchIdPw',
    name: 'SearchIdPwPage',
    component: SearchIdPwPage
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
