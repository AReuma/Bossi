<template>
  <div>
    <change-pw-view
      :email="email"
      @changePw="changePw"
    ></change-pw-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import ChangePwView from "@/components/user/search/ChangePwView.vue";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";

export default defineComponent({
  name: "ChangePwPage",
  components: {ChangePwView},
  props: {
    email: {
      type: String,
      default: "",
    }
  },
  methods: {
    changePw(payload){
      const {email, password} = payload;

      alert(password)
      axios.post(API_BASE_URL+'/api/v1/users/phone/changePw', {email, password})
          .then((res) => {
            console.log(res)
            this.$router.push({name: "LoginPage"})
          })
          .catch(() => {
            alert('에러')
          })
    }
  }
})
</script>

<style scoped>

</style>