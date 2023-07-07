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


export default defineComponent({
  name: "EmailJoinPage",
  components: {
    EmailJoinView

  },
  methods: {
    register(payload){
      const {email, password, nickName, phoneNum, recommender, select} = payload;

      axios.post('http://localhost:7777/user/register', {email, password, nickName, phoneNum, recommender, select})
          .then((res) => {
            alert(res)
          })
      console.log(payload);

    }
  }
})
</script>

<style scoped>

</style>