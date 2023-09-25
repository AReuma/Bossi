<template>
  <div>
    <seller-header-view></seller-header-view>
    <seller-product-create :categoryList="categoryList"></seller-product-create>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import SellerProductCreate from "@/components/seller/SellerProductCreate.vue";
import SellerHeaderView from "@/components/seller/SellerHeaderView.vue";
import {mapActions, mapState} from "vuex";

export default defineComponent({
  name: "SellerProductCreatePage",
  components: {SellerHeaderView, SellerProductCreate},
  computed: {
    ...mapState(['categoryList'])
  },
  mounted () {
    this.fetchCategoryList();
  },
  methods: {
    ...mapActions(['fetchCategoryList'])
  }

})
</script>

<style scoped>

</style>