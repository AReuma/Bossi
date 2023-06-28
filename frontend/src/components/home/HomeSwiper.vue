<template>
  <div>
    <swiper
        style="height: 460px"
        class="swiper"
        :options="swiperOption"
    >
      <swiper-slide v-for="(item, i) in swiperList" :key="i">
        <img :src="require(`../../assets/homeSwiper/${item}`)" height="480" width="1920"/></swiper-slide>
      <div
          class="swiper-pagination"
          slot="pagination"
      >
      </div>
    </swiper>

  </div>
</template>

<script>
import { Swiper,  SwiperSlide} from "vue-awesome-swiper";
import "swiper/css/swiper.css";

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
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
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