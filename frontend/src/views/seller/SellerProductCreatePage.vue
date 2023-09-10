<template>
  <div>
    <seller-product-create></seller-product-create>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SellerProductCreate from "@/components/seller/SellerProductCreate.vue";

export default defineComponent({
  name: "SellerProductCreatePage",
  components: {SellerProductCreate}
})
</script>

<style scoped>

</style>