<template>
  <div>
    <seller-main-header-view></seller-main-header-view>
    <seller-main-dashboard v-if="index"></seller-main-dashboard>
    <router-view v-else></router-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SellerMainHeaderView from "@/components/seller/main/SellerMainHeaderView.vue";
import SellerMainDashboard from "@/components/seller/main/SellerMainDashboard.vue";

export default defineComponent({
  name: "SellerMainPage",
  components: {SellerMainDashboard, SellerMainHeaderView},
  data(){
    return {
      index: true
    }
  },
  mounted() {
    console.log(this.$route.path)
    if(this.$route.path.search([/1-6/]) !== -1){
      this.index = false;
    }
  }
})
</script>

<style scoped>

</style>