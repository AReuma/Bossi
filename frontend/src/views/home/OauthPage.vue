<template>
  <div>

  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {ParsingInfo} from "@/LoginUtil";
import {SAVE_COOKIE_ACCESS, SAVE_COOKIE_REFRESH} from "@/constant/login";
import { useCookies } from "vue3-cookies";
export default defineComponent({
  name: "OauthPage",
  created() {
    const { cookies } = useCookies();
    this.token = this.$route.query;

    cookies.set('access_token', this.token.access_token, SAVE_COOKIE_ACCESS);
    cookies.set('refresh_token', this.token.refresh_token, SAVE_COOKIE_REFRESH);

    const openerWindow = window.opener;
    const routeHome = this.$router.resolve(
        {
          path: '/',
          name: 'HomePage',
        });

    const routeData = this.$router.resolve(
        {
          path: '/snsRegister',
          name: 'SnsRegisterPage',
        });

    setTimeout(() => {ParsingInfo(this.token.access_token)}, 500);

    const promise = new Promise((resolve) => {
      setTimeout(function () {
        console.log(cookies.get('registerStatus'))
        resolve(cookies.get('registerStatus'))
      }, 500)
    })

    promise.then((response) => {
      console.log(response)
      if(response === 'true') {
        openerWindow.document.location.href = routeHome.href; // 로그인 후 어디로?
      }else{
        openerWindow.document.location.href = routeData.href;
      }
      window.close();
    }, 500)


  /*  setTimeout( () =>
        window.close(), 500);*/
  }
})
</script>

<style scoped>

</style>