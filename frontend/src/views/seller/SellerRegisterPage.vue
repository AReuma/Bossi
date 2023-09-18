<template>
  <div>
    <seller-join-view @register="register"></seller-join-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SellerJoinView from "@/components/seller/SellerJoinView.vue";
import {API_BASE_URL} from "@/constant/basic";
import axios from "axios";

export default defineComponent({
  name: "SellerRegisterPage",
  components: {SellerJoinView},
  methods: {
    register(formData) {
      console.log(formData.get('storeIntroduction'))
      console.log(formData.get('profileImg'))
      /*let jsonData = payload.jsonData;
      const formData = new FormData();
      formData.append('profileImg', payload.profileImg)*/
      axios.post(API_BASE_URL+'/api/v1/seller/join', formData,{
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then((res) => {
            console.log(res)
          })
    }
  }
})
</script>

<style scoped>

</style>