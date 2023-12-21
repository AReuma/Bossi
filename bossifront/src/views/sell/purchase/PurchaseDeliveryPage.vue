<template>
  <div>
    <home-header></home-header>
    <purchase-delivery-view :purchaseInfo="purchaseInfo"></purchase-delivery-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import PurchaseDeliveryView from "@/components/sell/purchase/PurchaseDeliveryView.vue";
import HomeHeader from "@/components/home/HomeHeader.vue";
import {mapActions, mapState} from "vuex";
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "PurchaseDeliveryPage",
  components: {HomeHeader, PurchaseDeliveryView},
  data() {
    return {
      productId: useCookies().cookies.get('productId'),
      options: useCookies().cookies.get('options'),
      optionCount: useCookies().cookies.get('optionCount'),
      email: useCookies().cookies.get('email'),
    }
  },
  methods: {
    ...mapActions(['fetchPurchaseInfo']),
  },
  mounted() {
    this.fetchPurchaseInfo({productId: this.productId, options: this.options, optionCount: this.optionCount, email: this.email})
  },
  computed: {
    ...mapState(['purchaseInfo'])
  }
})
</script>

<style scoped>

</style>