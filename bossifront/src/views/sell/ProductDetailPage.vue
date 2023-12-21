<template>
  <div>
    <home-header></home-header>
    <div class="content-wrapper">
      {{userData}}
      <product-detail-content class="left-component" :productImg="productContent.productImg"></product-detail-content>
      <product-detail-info v-if="productContent && productContent.productOption && !isEmptyObject(productContent.productOption)" class="right-component" :productContent="productContent" @addCart="addCart"></product-detail-info>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import HomeHeader from "@/components/home/HomeHeader.vue";
import ProductDetailInfo from "@/components/sell/detail/ProductDetailInfo.vue";
import ProductDetailContent from "@/components/sell/detail/ProductDetailContent.vue";
import {mapActions, mapState} from "vuex";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import {isEmptyObject} from "@tiptap/vue-3";

export default defineComponent({
  name: "ProductDetailPage",
  components: {ProductDetailContent, ProductDetailInfo, HomeHeader},
  methods: {
    isEmptyObject,
    ...mapActions(['fetchProductContent', 'updateImgStyleData']),
    addCart(payload){
      const {email, productId, options, optionCount} = payload;
      console.log(email)

      axios.post(API_BASE_URL+"/api/v1/cart/addCart", {email, productId, options, optionCount})
          .then((res) => {
            console.log(res)
          })
          .catch((res) => {
            console.log(res)
          })
    }
  },
  computed: {
    ...mapState(["productContent"])
  },
  mounted() {
    let id = this.$route.query.id;
    this.fetchProductContent(id)
  }
})

</script>

<style scoped>

.content-wrapper {
  display: flex;
  max-width: 1200px;
  margin: 20px auto;
}

.left-component {
  flex: 1;
  margin-right: 20px;
}

.right-component {
  flex: 0 0 48%;
  position: sticky;
  top: 0;
  height: calc(100vh - (200));
  background-color: #fff;
}
</style>