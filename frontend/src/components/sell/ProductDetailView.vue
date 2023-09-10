<template>
  <div style="display: flex; height: 100vh; width: 100%; padding: 12px 16px; max-width: 1200px; margin: 20px auto; justify-content: space-between; border: 1px solid rebeccapurple">
    <product-detail-content :productContent="productContent"  style="margin-right: 20px;"></product-detail-content>
    <product-detail-info></product-detail-info>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import ProductDetailContent from "@/components/sell/detail/ProductDetailContent.vue";
import ProductDetailInfo from "@/components/sell/detail/ProductDetailInfo.vue";
import {mapActions, mapState} from "vuex";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {FETCH_PRODUCT_CONTENT} from "@/store/mutation-types";

export default defineComponent({
  name: "ProductDetailView",
  components: {ProductDetailInfo, ProductDetailContent},
  methods: {
    ...mapActions(['fetchProductContent'])
  },
  computed: {
    ...mapState(["productContent"])
  },
  mounted() {
    axios.get(API_BASE_URL+`/api/v1/product/1`)
        .then((res) => {
          this.$store.commit(FETCH_PRODUCT_CONTENT, res.data)
        })
        .catch((res) => {
          console.log(res)
        })
  }
})
</script>

<style scoped>

</style>