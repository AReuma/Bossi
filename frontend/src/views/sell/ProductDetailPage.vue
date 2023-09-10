<template>
  <div>
    <home-header></home-header>
    <product-detail-view></product-detail-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import ProductDetailView from "@/components/sell/ProductDetailView.vue";
import HomeHeader from "@/components/home/HomeHeader.vue";

export default defineComponent({
  name: "ProductDetailPage",
  components: {HomeHeader, ProductDetailView}
})
</script>

<style scoped>

</style>