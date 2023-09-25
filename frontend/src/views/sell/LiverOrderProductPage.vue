<template>
  <div>
    <home-header :clickCategory="index"></home-header>
    <liver-order-product-view :liverOrderProduct="liverOrderProduct"></liver-order-product-view>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import HomeHeader from "@/components/home/HomeHeader.vue";
import LiverOrderProductView from "@/components/sell/LiverOrderProductView.vue";
import {mapActions, mapState} from "vuex";

export default defineComponent({
  name: "LiverOrderProductPage",
  components: {LiverOrderProductView, HomeHeader},
  data() {
    return {
      index: 2
    }
  },
  mounted () {
    this.fetchLiverOrderProduct();
  },
  methods: {
    ...mapActions(['fetchLiverOrderProduct'])
  },
  computed: {
    ...mapState(["liverOrderProduct"])
  },
})
</script>

<style scoped>

</style>