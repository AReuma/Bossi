<template>
  <div>
    <home-header></home-header>
    <purchase-multi-delivery-view :multiOrderProduct="multiOrderProduct"></purchase-multi-delivery-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import HomeHeader from "@/components/home/HomeHeader.vue";
import {useCookies} from "vue3-cookies";
import {mapActions, mapState} from "vuex";
import PurchaseMultiDeliveryView from "@/components/sell/purchase/PurchaseMultiDeliveryView.vue";

export default defineComponent({
  name: "PurchaseMultiDeliveryPage",
  components: {PurchaseMultiDeliveryView, HomeHeader},
  data() {
    return {
      orderData: useCookies().cookies.get('orderData'),
      email: useCookies().cookies.get('email'),
    }
  },
  methods: {
    ...mapActions(['fetchMultiProductInfo']),
  },
  mounted() {
    //this.fetchPurchaseInfo({productId: this.productId, options: this.options, optionCount: this.optionCount, email: this.email})
    const orderData = JSON.parse(this.orderData);
    console.log(orderData)
    let email = this.email
    this.fetchMultiProductInfo({email, orderData})
  },
  computed: {
    ...mapState(['multiOrderProduct'])
  }
})
</script>

<style scoped>

</style>