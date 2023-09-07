<template>
  <div style="justify-content: center; align-items: center; display: flex; height: 100%; flex-direction: column; padding-bottom: 200px">

    <img src="../../../assets/logo/Bossi_logo_3.png" style="height: 150px; margin-bottom: 40px;" @click="moveHome" alt="logo"/>

    <span style="background-color: rgba(252,152,153,0.38); font-size: 25px">{{name}} 님은 접근 권한이 없습니다.</span>

    <p style="margin-top: 20px">관리자에게 문의해주세요</p>

  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "ManagerAccessDeniedPage",
  data(){
    return {
      name: useCookies().cookies.get("nickName")
    }
  },
  methods: {
    moveHome(){
      this.$router.push({name: "HomePage"})
    }
  }
})
</script>

<style scoped>

</style>