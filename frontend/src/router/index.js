import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '@/views/home/beforeLogin/HomePage.vue'
import LoginPage from "@/views/user/LoginPage.vue";
import JoinPage from "@/views/user/JoinPage.vue";
import EmailJoinPage from "@/views/user/EmailJoinPage.vue";
import OauthPage from "@/views/home/OauthPage.vue";
import SearchIdPwPage from "@/views/user/search/SearchIdPwPage.vue";
import SearchMailIdPwPage from "@/views/user/search/SearchMailIdPwPage.vue";
import SearchPhoneIdPwPage from "@/views/user/search/SearchPhoneIdPwPage.vue";
import SearchIdPwFoundPage from "@/views/user/search/SearchIdPwFoundPage.vue";
import ChangePwPage from "@/views/user/search/ChangePwPage.vue";

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
    path: '/v1/auth/find',
    name: 'SearchIdPwPage',
    component: SearchIdPwPage
  },
  {
    path: '/v1/auth/find/mail',
    name: 'SearchMailIdPwPage',
    component: SearchMailIdPwPage
  },
  {
    path: '/v1/auth/find/phone',
    name: 'SearchPhoneIdPwPage',
    component: SearchPhoneIdPwPage
  },
  {
    path: '/v1/auth/find/phone/found',
    name: 'SearchIdPwFoundPage',
    component: SearchIdPwFoundPage,
    props: true
  },
  {
    path: '/v1/auth/find/phone/changePw',
    name: "ChangePwPage",
    component: ChangePwPage,
    props: true
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
