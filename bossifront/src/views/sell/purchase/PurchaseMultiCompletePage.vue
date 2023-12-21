<template>
  <div>
    <home-header></home-header>
    <purchase-complete-view :orderCompleteInfo="orderCompleteInfo"></purchase-complete-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import PurchaseCompleteView from "@/components/sell/purchase/PurchaseCompleteView.vue";
import {mapActions, mapState} from "vuex";
import HomeHeader from "@/components/home/HomeHeader.vue";

export default defineComponent({
  name: "PurchaseMultiCompletePage",
  components: {HomeHeader, PurchaseCompleteView},
  data(){
    return {
      orderNum: this.$route.query.orderNum
    }
  },
  methods: {
    ...mapActions(['fetchOrderCompleteInfo'])
  },
  computed: {
    ...mapState(['orderCompleteInfo'])
  },
  mounted() {
    console.log(this.orderNum)
    this.fetchOrderCompleteInfo(this.orderNum)
  },
  created() {
  }
})
</script>

<style scoped>

</style>