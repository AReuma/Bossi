<template>
  <div style="display: flex; height: 100vh; width: 100%; padding: 12px 16px; max-width: 1200px; margin: 20px auto; justify-content: space-between; border: 1px solid rebeccapurple">
    <product-detail-content class="left-component" style="margin-right: 20px;"></product-detail-content>
    <product-detail-info class="right-component" :productContent="productContent"></product-detail-info>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import ProductDetailContent from "@/components/sell/detail/ProductDetailContent.vue";
import ProductDetailInfo from "@/components/sell/detail/ProductDetailInfo.vue";
import {mapActions, mapState} from "vuex";

export default defineComponent({
  name: "ProductDetailView",
  components: {ProductDetailInfo, ProductDetailContent},
  methods: {
    ...mapActions(['fetchProductContent', 'updateImgStyleData'])
  },
  computed: {
    ...mapState(["productContent"])
  },
  mounted() {
    let id = this.$route.query.id;
    this.fetchProductContent(id)
    //this.updateImgStyleData(this.productContent.content)

    //this.$store.dispatch('updateImgStyleData', doc.documentElement.outerHTML);
  }
})
</script>

<style scoped>
.left-component {
  flex: 1; /* 좌측 컴포넌트는 화면 너비의 1/2를 차지 */
}

.right-component {
  flex: 1; /* 우측 컴포넌트는 화면 너비의 1/2를 차지 */
  position: fixed;
  right: 0;
  top: 0;
  height: 100%; /* 전체 높이로 설정 */
  background-color: #fff; /* 고정된 영역 배경색 */
  border-left: 1px solid #ccc; /* 좌우 구분선 */
}
</style>