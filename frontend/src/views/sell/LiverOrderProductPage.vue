<template>
  <div>
    <home-header :clickCategory="index"></home-header>
    <liver-order-product-view></liver-order-product-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import HomeHeader from "@/components/home/HomeHeader.vue";
import LiverOrderProductView from "@/components/sell/LiverOrderProductView.vue";

export default defineComponent({
  name: "LiverOrderProductPage",
  components: {LiverOrderProductView, HomeHeader},
  data() {
    return {
      index: 2
    }
  }
})
</script>

<style scoped>

</style>