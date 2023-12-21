<template>
  <div>
    <swiper
        style="height: 460px"
        class="swiper"
        v-bind="swiperOption"
    >
      <swiper-slide v-for="(item, i) in swiperList" :key="i">
        <img :src="require(`../../assets/homeSwiper/${item}`)" height="480" width="1920"/></swiper-slide>
    </swiper>

  </div>
</template>

<script>
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";

export default {
  name: "HomeSwiper",
  components: {
    Swiper,
    SwiperSlide
  },
  data() {
    return {
      swiperList: ['swiper1.jpg', 'swiper2.jpg', 'swiper3.jpg', 'swiper4.jpg'],
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 30,
        autoplay:{
          delay: 1500,
          disableOnInteraction: true
        },
        speed: 2,
        autoHeight: true,
        loop: true,
      }
    }
  }
}
</script>

<style scoped>
#img{
  width: 100%; height: 500px;
  max-width: 100%;
  max-height: 500px;
}
</style>

<style lang="scss">
.swiper-pagination-bullets {
  background-color: red !important;
}
</style>