<template>
  <div>
    <email-join-view
      @register="register">
    </email-join-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import EmailJoinView from "@/components/user/EmailJoinView.vue";
import axios from "axios";
import {useCookies} from "vue3-cookies";


export default defineComponent({
  name: "EmailJoinPage",
  components: {
    EmailJoinView

  },
  data(){
    return {
      cookieName: useCookies().cookies.get('name'),
    }
  },
  methods: {
    register(payload){
      const {email, password, nickName, phoneNum, recommender, checkSMS} = payload;
      const registerStatus = true;
      axios.post('http://localhost:7777/api/v1/users/join', {email, password, nickName, phoneNum, recommender, checkSMS, registerStatus})
          .then((res) => {
            alert(res)
          })
          .catch((res) => {
            console.log(res)
          })
      console.log(payload);

    }
  },
})
</script>

<style scoped>

</style>