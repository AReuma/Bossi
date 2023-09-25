<template>
  <div>
    <home-header></home-header>
    <purchase-delivery-view></purchase-delivery-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {bus} from "@/constant/EventBus";
import PurchaseDeliveryView from "@/components/sell/purchase/PurchaseDeliveryView.vue";
import HomeHeader from "@/components/home/HomeHeader.vue";

export default defineComponent({
  name: "PurchaseDeliveryPage",
  components: {HomeHeader, PurchaseDeliveryView},
  data() {
    return {
      receivedData: ''
    }
  },
  mounted() {
    bus.$on('send-data', data => {
      console.log("!: "+ data)
      this.receivedData = data;
      console.log(this.receivedData)
    });
  }
})
</script>

<style scoped>

</style>