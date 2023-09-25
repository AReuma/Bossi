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
import EnteringStorePage from "@/views/manager/EnteringStorePage.vue";
import ManagerWaitingListView from "@/components/manager/auth/ManagerWaitingListView.vue";
import ManagerMainPage from "@/views/manager/auth/ManagerMainPage.vue";
import ManagerAccessDeniedPage from "@/views/manager/auth/ManagerAccessDeniedPage.vue";
import LiverOrderProductPage from "@/views/sell/LiverOrderProductPage.vue";
import ProductDetailPage from "@/views/sell/ProductDetailPage.vue";
import SellerProductCreatePage from "@/views/seller/SellerProductCreatePage.vue";
import SellerMainPage from "@/views/seller/SellerMainPage.vue";
import SellerMainDashboard from "@/components/seller/main/SellerMainDashboard.vue";
import SellerRegisterPage from "@/views/seller/SellerRegisterPage.vue";
import SellerLoginPage from "@/views/seller/SellerLoginPage.vue";
import PurchaseDirectPage from "@/views/sell/purchase/PurchaseDirectPage.vue";
import PurchaseDeliveryPage from "@/views/sell/purchase/PurchaseDeliveryPage.vue";

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
    path: '/user/me/favoriteproduct',
    name: "FavoriteProductPage",
    component: FavoriteProductPage
  },
  {
    path: '/user/me/coupon',
    name: "MyCouponListPage",
    component: MyCouponListPage
  },
  {
    path: '/user/me/personal',
    name: "PersonalInfoPage",
    component: PersonalInfoPage
  },
  {
    path: '/user/cart',
    name: "MyCartPage",
    component: MyCartPage
  },
  {
    path: '/user/enterStore',
    name: "EnteringStorePage",
    component: EnteringStorePage
  },
  { path: '/manager/category', component: ManagerMainPage, name: "ManagerMainPage",
    children: [
      {
        path: '1',
        component: ManagerWaitingListView
      },
      {
        path: '2',
        component: ManagerWaitingListView,
      },
      {
        path: '3',
        component: ManagerWaitingListView
      },
      {
        path: '4',
        component: ManagerWaitingListView
      },
      {
        path: '5',
        component: ManagerWaitingListView
      }
    ]
  },
  {
    path: '/manager/denied',
    name: "ManagerAccessDeniedPage",
    component: ManagerAccessDeniedPage
  },
  {
    path: '/sell/liver-order-product',
    name: "LiverOrderProductPage",
    component: LiverOrderProductPage
  },
  {
    path: '/sell/product',
    name: "ProductDetailPage",
    component: ProductDetailPage
  },
  {
    path: '/seller/product/create',
    name: "SellerProductCreatePage",
    component: SellerProductCreatePage
  },
  { path: '/seller/main', component: SellerMainPage, name: "SellerMainPage",
    children: [
      {
        path: '1',
        component: SellerMainDashboard
      },
      {
        path: '2',
        component: SellerMainDashboard,
      },
      {
        path: '3',
        component: SellerMainDashboard
      },
      {
        path: '4',
        component: SellerMainDashboard
      },
      {
        path: '5',
        component: SellerMainDashboard
      },
      {
        path: '6',
        component: SellerMainDashboard
      }
    ]
  },
  {
    path: '/seller/join',
    name: "SellerRegisterPage",
    component: SellerRegisterPage
  },
  {
    path: '/seller/login',
    name: "SellerLoginPage",
    component: SellerLoginPage
  },
  {
    path: '/cart/direct_new',
    name: "PurchaseDirectPage",
    component: PurchaseDirectPage,
    props: true
  },
  {
    path: '/cart/payment',
    name: "PurchaseDeliveryPage",
    component: PurchaseDeliveryPage
  }
]


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
