<template>
  <div style="height: 100%; max-width: 50%; padding: 10px;">
    <div style="width: 100%;">
      <swiper
          style="width: 100%; height: 100%"
          class="swiper"
          :options="swiperOption"
      >
        <swiper-slide v-for="(item, i) in productImg" :key="i">
          <v-img :src="`https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/${item}`" width="100%" height="520"/></swiper-slide>

        <div
            class="swiper-pagination"
            slot="pagination"
        >
        </div>
      </swiper>
    </div>
    <hr style="margin-top: 10px; padding-right: 10px; border: 1px solid rgba(187,187,187,0.18)">
    <div style="width: 100%; text-align: center; margin-top: 25px" v-html="userData">
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {Swiper, SwiperSlide} from "vue-awesome-swiper";
import 'swiper/css/swiper.css'

export default defineComponent({
  name: "ProductDetailContent",
  components: {
    Swiper,
    SwiperSlide,
  },
  props: {
    productImg: {
      type: Array
    }
  },
  computed: {
    userData() {
      //console.log(this.$store.getters.getImgData)
      return this.$store.getters.getImgData;
    }
  },
  data(){
    return{
      swiperList: ['product_test.jpg', 'product_test_1.jpg', 'product_test_2.jpg', 'product_test_3.jpg'],
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 30,
        /*autoplay:{
          delay: 1500,
          disableOnInteraction: true
        },*/
        speed: 2,
        autoHeight: true,
        loop: false,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      },
      content: '',

    }
  },
  created() {


  }
})
</script>

<style scoped>

.swiper-pagination {
  --swiper-theme-color: #fc9899
}

.content img {
  width: 50%;
}
</style>