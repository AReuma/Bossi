<template>
  <div id="basic">
    <div style="margin-top: 20px; width: 100%; border-bottom: 1px solid rgba(67,79,88,0.2);; margin-bottom: 30px; padding-bottom: 5px; font-size: 24px; font-family: 'GmarketSansBold', sans-serif;">
      실시간 구매
    </div>

    <div style="display: flex;">
    <v-card flat style="margin: 5px" v-for="i in n" :key="i" outlined height="400" width="225">
      <div style="position: relative;height: 210px">
        <v-img class="product-image" style="position: absolute; z-index: 1; border-bottom-left-radius: 0; border-bottom-right-radius: 0" src="../../../assets/sellProduct/product_test.jpg"/>
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

    <div>
      <v-btn depressed width="500" text height="50" style="margin-top: 30px; border: 1px solid rgba(67,79,88,0.2)">실시간 구매 더보기</v-btn>
    </div>

  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "liveOrderList",
  data(){
    return {
      n: 5,
      starNum: 3.5,
    }
  },
  inject: {
    theme: {
      default: { isDark: false },
    },
  },
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