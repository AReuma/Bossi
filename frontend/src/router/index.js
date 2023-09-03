import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '@/views/home/HomePage.vue'
import LoginPage from "@/views/user/LoginPage.vue";
import JoinPage from "@/views/user/JoinPage.vue";
import EmailJoinPage from "@/views/user/EmailJoinPage.vue";
import OauthPage from "@/views/home/OauthPage.vue";
import SearchIdPwPage from "@/views/user/search/SearchIdPwPage.vue";
import SearchMailIdPwPage from "@/views/user/search/SearchMailIdPwPage.vue";
import SearchPhoneIdPwPage from "@/views/user/search/SearchPhoneIdPwPage.vue";
import SearchIdPwFoundPage from "@/views/user/search/SearchIdPwFoundPage.vue";
import ChangePwPage from "@/views/user/search/ChangePwPage.vue";
import MyPage from "@/views/user/myPage/MyPage.vue";
import FavoriteProductPage from "@/views/user/myPage/FavoriteProductPage.vue";
import MyOrderDeliveryPage from "@/views/user/myPage/MyOrderDeliveryPage.vue";
import MyCouponListPage from "@/views/user/myPage/MyCouponListPage.vue";
import PersonalInfoPage from "@/views/user/myPage/PersonalInfoPage.vue";
import MyCartPage from "@/views/order/MyCartPage.vue";

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
  },
  {
    path: '/user/me',
    name: "MyPage",
    component: MyPage
  },
  {
    path: '/user/me/order/payment',
    name: "MyOrderDeliveryPage",
    component: MyOrderDeliveryPage
  },
  {
    path: 'user/me/favoriteproduct',
    name: "FavoriteProductPage",
    component: FavoriteProductPage
  },
  {
    path: 'user/me/coupon',
    name: "MyCouponListPage",
    component: MyCouponListPage
  },
  {
    path: 'user/me/personal',
    name: "PersonalInfoPage",
    component: PersonalInfoPage
  },
  {
    path: 'user/cart',
    name: "MyCartPage",
    component: MyCartPage
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
