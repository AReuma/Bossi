<template>
  <div style="width: 100%; height: 100%">
    <div style="width: 100%; height: 250px; background-color: #fc9899; display: flex; justify-content: center; align-items: center; flex-direction: column">
      <p style="font-size: 35px; color: white">실시간 구매</p>
      <p style="font-size: 20px; color: white">지금 이순간! 다른사람들이 구매하는 작품들이에요</p>
    </div>

    <div id="basic">
      <div style="display: flex;">
        <v-card flat style="margin: 5px" v-for="i in n" :key="i" outlined height="400" width="225" @click="productClick">
          <div style="position: relative; height: 210px">
            <v-img class="product-image" style="position: absolute; z-index: 1; border-bottom-left-radius: 0; border-bottom-right-radius: 0" src="../../assets/sellProduct/product_test.jpg"/>
            <v-icon color="white" size="30" style="z-index: 2; position: absolute; right: 15px; top: 15px;">mdi-heart-outline</v-icon>
          </div>

          <div style="height: 100%">
            <div style="padding: 10px;">
              <span style="font-size: 15px; color: #bbbbbb">딥드</span>
              <p>우리쌀 수제 팝쌀약과 2개 세트 쌀조청으로 만든 수제집청</p>

            </div>

            <div style="border-top: 1px solid rgba(220,220,220,0.72); padding: 2px 10px">
              <v-rating v-model="starNum" size="18" background-color="gray" dense half-increments color="rgb(255,183,0)" readonly>
              </v-rating>

              <div style="margin-top: 2px; font-size: 14px; color: #5f5f5f" >
                좋아요! 또 시키러 갑니다!
              </div>
            </div>
          </div>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "LiverOrderProductView",
  data(){
    return {
      starNum: 4,
      n: 5
    }
  },
  methods: {
    productClick(){
      this.$router.push({name: "ProductDetailPage"})
    }
  }
})
</script>

<style scoped>
#basic{
  max-width: 1200px;
  height: 100%;
  padding: 12px 16px;
  width: 100%;
  margin: 0 auto;
  align-items: center;
  display: flex;
  justify-content: center;
  flex-direction: column;
}
.product-image {
  width: 100%;
  height: 210px;

}
</style>