<template>
  <div>
    <home-header></home-header>
    <purchase-direct-view v-if="directOrderList" :directOrderList="directOrderList" :cartIsEmpty="cartIsEmpty"></purchase-direct-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import PurchaseDirectView from "@/components/sell/purchase/PurchaseDirectView.vue";
import HomeHeader from "@/components/home/HomeHeader.vue";
import {useCookies} from "vue3-cookies";
import {mapActions, mapState} from "vuex";

export default defineComponent({
  name: "PurchaseDirectPage",
  components: {HomeHeader, PurchaseDirectView},
  data(){
    return {
      productId: useCookies().cookies.get('productId'),
      options: useCookies().cookies.get('options'),
      optionCount: useCookies().cookies.get('optionCount'),
      cartIsEmpty: false
    }
  },
  methods: {
    ...mapActions(['fetchDirectOrderList']),
    order(payload){
      const {orderMsg} = payload;

      console.log(orderMsg)
      console.log('productId: '+this.productId)
      console.log('options: '+this.options)
      console.log('optionCount: '+this.optionCount)


    }
  },
  mounted() {
    if(this.options !== null) {
      this.cartIsEmpty = false
      this.fetchDirectOrderList({productId: this.productId, options: this.options, optionCount: this.optionCount})
    } else {
      this.cartIsEmpty = true;
    }
  },
  computed: {
    ...mapState(["directOrderList"])
  }
})
</script>

<style scoped>

</style>