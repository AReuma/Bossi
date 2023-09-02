<template>
  <div>
    <home-header></home-header>
    <home-swiper></home-swiper>
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeSwiper from "@/components/home/HomeSwiper.vue";

export default {
  name: 'LoginHomePage',
  components: {
    HomeSwiper,
    HomeHeader
  },
}
</script>
