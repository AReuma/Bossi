<template>
  <div style="width: 100%; height: 100%">
    <div style="width: 100%; height: 250px; background-color: #fc9899; display: flex; justify-content: center; align-items: center; flex-direction: column">
      <p style="font-size: 35px; color: white">실시간 구매</p>
      <p style="font-size: 20px; color: white">지금 이순간! 다른사람들이 구매하는 작품들이에요</p>
    </div>

    <div id="basic">
      <div style="display: flex;">
        <v-card flat style="margin: 5px" v-for="(i, index) in liverOrderProduct" :key="index" outlined height="400" width="225" @click="productClick(i.productId)">
          <div style="position: relative; height: 210px">
            <v-img class="product-image" style="position: absolute; z-index: 1; border-bottom-left-radius: 0; border-bottom-right-radius: 0" :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${i.productImg}`"/>
            <v-icon color="white" size="30" style="z-index: 2; position: absolute; right: 15px; top: 15px;">mdi-heart-outline</v-icon>
          </div>

          <div style="height: 100%">
            <div style="padding: 10px;">
              <span style="font-size: 15px; color: #bbbbbb">{{i.storeName}}</span>
              <p>{{i.productTitle}}</p>

            </div>

            <div style="border-top: 1px solid rgba(220,220,220,0.72); padding: 2px 10px">
              <v-rating v-model="i.rating" size="18" background-color="gray" dense half-increments color="rgb(255,183,0)" readonly>
              </v-rating>

              <div style="margin-top: 2px; font-size: 14px; color: #5f5f5f" >
                {{i.preview}}
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
  props: {
    liverOrderProduct: {
      type: Array
    }
  },
  data(){
    return {
      starNum: 4,
      n: 5
    }
  },
  methods: {
    productClick(index){
      console.log(index)
      this.$router.push({name: "ProductDetailPage", query: {id: index}})
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