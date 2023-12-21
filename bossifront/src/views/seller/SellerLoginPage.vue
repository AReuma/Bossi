<template>
  <div>
    <seller-login-view @login="login" :showError="showError"></seller-login-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SellerLoginView from "@/components/seller/SellerLoginView.vue";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {SAVE_COOKIE_ACCESS, SAVE_COOKIE_REFRESH} from "@/constant/login";
import { useCookies } from "vue3-cookies";
import {ParsingSellerInfo} from "@/SellerLoginUtil";

export default defineComponent({
  name: "SellerLoginPage",
  components: {SellerLoginView},
  data(){
    return {
      showError: false,
    }
  },
  methods: {
    login(payload){
      const {email, password} = payload

      axios.post(API_BASE_URL+'/api/v1/seller/login', {email, password})
          .then((res) => {
            if(res.status === 200){
              useCookies().cookies.set('access_token', res.data.access_token, SAVE_COOKIE_ACCESS);
              useCookies().cookies.set('refresh_token', res.data.refresh_token, SAVE_COOKIE_REFRESH);

              setTimeout(() => {ParsingSellerInfo(res.data.access_token)}, 500);
              this.$router.push({name: 'SellerMainPage'})
            }
          })
          .catch((res) => {
            console.log(res+'error')
            this.showError = true
          })
    }
  }
})
</script>

<style scoped>

</style>