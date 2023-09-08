<template>
  <div>
    <entering-store-view
        @submit="submit"
        :existCheck = existCheck
    ></entering-store-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import EnteringStoreView from "@/components/manager/EnteringStoreView.vue";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";

export default defineComponent({
  name: "enteringStorePage",
  components: {EnteringStoreView},
  data(){
    return {
      existCheck: false,
    }
  },
  methods: {
    submit(payload){
      const {email, sendEmail} = payload;

      axios.post(API_BASE_URL+"/api/v1/users/enteringStore", {email, sendEmail})
          .then((res) => {
            //let reg = /[^가-힣]/g;
            this.$router.go(0);
            console.log(res)
          })
          .catch((res) => {
            if(res.response.status === 409){
              this.existCheck = true;
            }
          })
      //kuuniin@gmail.com
    }
  }
})
</script>

<style scoped>

</style>