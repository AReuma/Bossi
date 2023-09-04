<template>
  <div>
    <search-id-pw-found-view
        :email="email"
        :socialType="socialType"
        :phone="phone"
    ></search-id-pw-found-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SearchIdPwFoundView from "@/components/user/search/SearchIdPwFoundView.vue";

export default defineComponent({
  name: "SearchIdPwFoundPage",
  components: {SearchIdPwFoundView},
  props: {
    email: {
      type: String,
      default: "",
    },
    socialType: {
      type: String,
      default: ""
    },
    phoneNum: {
      type: String,
      default: ""
    }
  },
  data(){
    return {
      phone: ''
    }
  },
  created() {
    this.phone = this.phoneNum.slice(0,3) + '-' + this.phoneNum.slice(3, 7) + '-' + this.phoneNum.slice(7, 11);
  }
})
</script>

<style scoped>

</style>