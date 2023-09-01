<template>
  <div>
    <login-view @login="login" :showError="showError"></login-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import LoginView from "@/components/user/LoginView.vue";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {ParsingInfo} from "@/LoginUtil";
import {SAVE_COOKIE_ACCESS, SAVE_COOKIE_REFRESH} from "@/constant/login";
import { useCookies } from "vue3-cookies";

export default defineComponent({
  name: "LoginPage",
  components: {LoginView},
  data(){
    return {
      showError: false,
    }
  },
  methods: {
    login(payload) {
      const {email, password} = payload;

      axios.post(API_BASE_URL+'/api/v1/users/login', {email, password})
          .then((res) => {
            this.showError = false;
            const {cookies} = useCookies();

            console.log(res);

            cookies.set('access_token', res.data.access_token, SAVE_COOKIE_ACCESS);
            cookies.set('refresh_token', res.data.refresh_token, SAVE_COOKIE_REFRESH);

            ParsingInfo(res.data.access_token);

            // 첫 방문일 경우
            if(cookies.get('registerStatus') === true){
              alert('첫 방문자')
            } else
              this.$router.push({name: 'HomePage'});


          })
          .catch((res) => {
            console.log(res);
            this.showError = true;
          })
    }
  }
})
</script>

<style scoped>

</style>