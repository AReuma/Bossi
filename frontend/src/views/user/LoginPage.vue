<template>
  <div>
    <login-view></login-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import LoginView from "@/components/user/LoginView.vue";

export default defineComponent({
  name: "LoginPage",
  components: {LoginView}
})
</script>

<style scoped>

</style>